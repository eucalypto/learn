package net.eucalypto.bignerdranch.photogallery.gallery

import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.*
import net.eucalypto.bignerdranch.photogallery.R
import net.eucalypto.bignerdranch.photogallery.backgroundpoll.DynamicShowNotificationReceiver
import net.eucalypto.bignerdranch.photogallery.backgroundpoll.PollWorker
import net.eucalypto.bignerdranch.photogallery.databinding.FragmentPhotoGalleryBinding
import net.eucalypto.bignerdranch.photogallery.databinding.ListItemGalleryBinding
import net.eucalypto.bignerdranch.photogallery.model.GalleryItem
import net.eucalypto.bignerdranch.photogallery.repository.QueryPreferences
import net.eucalypto.bignerdranch.photogallery.repository.ThumbnailDownloader
import timber.log.Timber
import java.util.concurrent.TimeUnit

private const val POLL_WORK = "POLL_WORK"

class PhotoGalleryFragment : Fragment() {

    private val viewModel: PhotoGalleryViewModel by viewModels()

    private lateinit var thumbnailDownloader: ThumbnailDownloader<PhotoAdapter.PhotoHolder>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
        DynamicShowNotificationReceiver.setUpWith(this)

        retainInstance = true
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_photo_gallery, menu)

        val searchItem = menu.findItem(R.id.menu_item_search)
        val searchView = searchItem.actionView as SearchView

        searchView.apply {

            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    Timber.d("QueryTextSubmit: $query")
                    viewModel.fetchPhotos(query)
                    return true
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    Timber.d("Query Text changed: $newText")
                    return false
                }

            })

            setOnSearchClickListener {
                setQuery(viewModel.searchTerm, false)
            }
        }

        setPollingTitle(menu)
    }

    private fun setPollingTitle(menu: Menu) {
        val toggleItem = menu.findItem(R.id.menu_item_toggle_polling)
        val isPolling = QueryPreferences.isPolling(requireContext())
        val toggleItemTitle =
            if (isPolling) R.string.stop_polling else R.string.start_polling
        toggleItem.setTitle(toggleItemTitle)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_item_clear -> {
                viewModel.fetchPhotos("")
                true
            }
            R.id.menu_item_toggle_polling -> {
                onPollingItemClicked()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun onPollingItemClicked() {
        val isPolling = QueryPreferences.isPolling(requireContext())
        if (isPolling) {
            stopPeriodicPolling()
            QueryPreferences.setPolling(requireContext(), false)
        } else {
            startPeriodicPolling()
            QueryPreferences.setPolling(requireContext(), true)
        }
        activity?.invalidateOptionsMenu()
    }

    private fun stopPeriodicPolling() {
        WorkManager.getInstance(requireContext())
            .cancelUniqueWork(POLL_WORK)
        Timber.d("Stop Periodic Polling Worker")
    }

    private fun startPeriodicPolling() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .build()
        val periodicRequest = PeriodicWorkRequest
            .Builder(PollWorker::class.java, 15, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .build()
        WorkManager.getInstance(requireContext()).enqueueUniquePeriodicWork(
            POLL_WORK,
            ExistingPeriodicWorkPolicy.KEEP,
            periodicRequest
        )
        Timber.d("Start periodic polling worker")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentPhotoGalleryBinding.inflate(
            inflater, container, false
        ).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewBinding = FragmentPhotoGalleryBinding.bind(view)

        with(viewBinding) {
            photoRecyclerView.layoutManager = GridLayoutManager(context, 3)
            viewModel.galleryItemLiveData.observe(viewLifecycleOwner) { galleryItems ->
                photoRecyclerView.adapter =
                    PhotoAdapter(galleryItems, thumbnailDownloader)
            }
        }

        val handler = Handler(Looper.getMainLooper())
        thumbnailDownloader =
            ThumbnailDownloader(handler) { photoHolder, bitmap ->
                val drawable = BitmapDrawable(resources, bitmap)
                photoHolder.bindDrawable(drawable)
            }
        lifecycle.addObserver(thumbnailDownloader.fragmentLifecycleObserver)
        viewLifecycleOwner.lifecycle.addObserver(thumbnailDownloader.viewLifecycleObserver)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewLifecycleOwner.lifecycle.removeObserver(thumbnailDownloader.viewLifecycleObserver)
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycle.removeObserver(thumbnailDownloader.fragmentLifecycleObserver)
    }

    private class PhotoAdapter(
        private val galleryItems: List<GalleryItem>,
        private val thumbnailDownloader: ThumbnailDownloader<PhotoHolder>
    ) :
        RecyclerView.Adapter<PhotoAdapter.PhotoHolder>() {


        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): PhotoHolder {
            return PhotoHolder.from(parent)
        }

        override fun onBindViewHolder(holder: PhotoHolder, position: Int) {
            val galleryItem = galleryItems[position]

            thumbnailDownloader.queueThumbnail(holder, galleryItem.url)
        }

        override fun getItemCount(): Int {
            return galleryItems.size
        }

        class PhotoHolder private constructor(viewBinding: ListItemGalleryBinding) :
            RecyclerView.ViewHolder(viewBinding.root) {

            private val itemImageView = viewBinding.photoGalleryItem

            fun bindDrawable(drawable: Drawable) {
                itemImageView.setImageDrawable(drawable)
            }

            companion object {
                fun from(parent: ViewGroup): PhotoHolder {
                    val layoutInflater = LayoutInflater.from(parent.context)
                    val imageItemBinding =
                        ListItemGalleryBinding.inflate(layoutInflater)
                    return PhotoHolder(imageItemBinding)
                }
            }
        }
    }

}