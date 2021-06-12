package net.eucalypto.bignerdranch.photogallery.gallery

import android.content.Intent
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import net.eucalypto.bignerdranch.photogallery.databinding.ListItemGalleryBinding
import net.eucalypto.bignerdranch.photogallery.model.GalleryItem
import net.eucalypto.bignerdranch.photogallery.repository.ThumbnailDownloader

class PhotoAdapter(
    private val galleryItems: List<GalleryItem>,
    private val thumbnailDownloader: ThumbnailDownloader<PhotoHolder>
) :
    RecyclerView.Adapter<PhotoHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PhotoHolder {
        return PhotoHolder.from(parent, thumbnailDownloader)
    }

    override fun onBindViewHolder(holder: PhotoHolder, position: Int) {
        val galleryItem = galleryItems[position]
        holder.bindTo(galleryItem)
    }

    override fun getItemCount(): Int {
        return galleryItems.size
    }
}


class PhotoHolder private constructor(
    viewBinding: ListItemGalleryBinding,
    private val thumbnailDownloader: ThumbnailDownloader<PhotoHolder>
) :
    RecyclerView.ViewHolder(viewBinding.root) {

    private val itemImageView = viewBinding.photoGalleryItemView
    private lateinit var galleryItem: GalleryItem
    private val context = itemImageView.context

    init {
        itemImageView.setOnClickListener {
            onClick()
        }
    }

    private fun onClick() {
        val intent =
            Intent(Intent.ACTION_VIEW, galleryItem.photoPageUri)
        context.startActivity(intent)
    }

    fun bindDrawable(drawable: Drawable) {
        itemImageView.setImageDrawable(drawable)
    }

    fun bindTo(galleryItem: GalleryItem) {
        this.galleryItem = galleryItem
        thumbnailDownloader.queueThumbnail(this, galleryItem.url)
    }

    companion object {
        fun from(
            parent: ViewGroup,
            thumbnailDownloader: ThumbnailDownloader<PhotoHolder>
        ): PhotoHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val imageItemBinding =
                ListItemGalleryBinding.inflate(layoutInflater)
            return PhotoHolder(imageItemBinding, thumbnailDownloader)
        }
    }
}