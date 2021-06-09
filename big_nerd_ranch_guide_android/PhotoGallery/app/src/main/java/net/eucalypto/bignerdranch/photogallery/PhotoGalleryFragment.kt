package net.eucalypto.bignerdranch.photogallery

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
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import net.eucalypto.bignerdranch.photogallery.databinding.FragmentPhotoGalleryBinding
import net.eucalypto.bignerdranch.photogallery.databinding.ListItemGalleryBinding
import timber.log.Timber

class PhotoGalleryFragment : Fragment() {

    private val viewModel: PhotoGalleryViewModel by viewModels()

    private lateinit var thumbnailDownloader: ThumbnailDownloader<PhotoAdapter.PhotoHolder>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)

        retainInstance = true

        setUpWorkRequest()
    }

    private fun setUpWorkRequest() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .build()
        val workRequest = OneTimeWorkRequest
            .Builder(PollWorker::class.java)
            .setConstraints(constraints)
            .build()
        WorkManager.getInstance().enqueue(workRequest)
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

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_item_clear -> {
                viewModel.fetchPhotos("")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
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