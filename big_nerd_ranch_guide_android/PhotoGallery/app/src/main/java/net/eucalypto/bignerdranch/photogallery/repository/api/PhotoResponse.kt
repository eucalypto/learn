package net.eucalypto.bignerdranch.photogallery.repository.api

import com.google.gson.annotations.SerializedName
import net.eucalypto.bignerdranch.photogallery.model.GalleryItem

class PhotoResponse {
    @SerializedName("photo")
    lateinit var galleryItems: List<GalleryItem>
}