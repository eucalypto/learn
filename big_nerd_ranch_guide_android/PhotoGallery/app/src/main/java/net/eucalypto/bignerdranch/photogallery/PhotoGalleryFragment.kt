package net.eucalypto.bignerdranch.photogallery

import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import net.eucalypto.bignerdranch.photogallery.databinding.FragmentPhotoGalleryBinding
import net.eucalypto.bignerdranch.photogallery.databinding.ListItemGalleryBinding

class PhotoGalleryFragment : Fragment() {

    private val viewModel: PhotoGalleryViewModel by viewModels()

    private lateinit var thumbnailDownloader: ThumbnailDownloader<PhotoAdapter.PhotoHolder>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        retainInstance = true
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
            return PhotoHolder.create(parent)
        }

        override fun onBindViewHolder(holder: PhotoHolder, position: Int) {
            val galleryItem = galleryItems[position]

            thumbnailDownloader.queueThumbnail(holder, galleryItem.url)
        }

        override fun getItemCount(): Int {
            return galleryItems.size
        }

        class PhotoHolder(viewBinding: ListItemGalleryBinding) :
            RecyclerView.ViewHolder(viewBinding.root) {

            private val itemImageView = viewBinding.photoGalleryItem

            fun bindDrawable(drawable: Drawable) {
                itemImageView.setImageDrawable(drawable)
            }

            companion object {
                fun create(parent: ViewGroup): PhotoHolder {
                    val layoutInflater = LayoutInflater.from(parent.context)
                    val imageItemBinding =
                        ListItemGalleryBinding.inflate(layoutInflater)
                    return PhotoHolder(imageItemBinding)
                }
            }
        }
    }

}