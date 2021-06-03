package net.eucalypto.bignerdranch.photogallery.api

import com.google.gson.annotations.SerializedName
import net.eucalypto.bignerdranch.photogallery.GalleryItem

class PhotoResponse {
    @SerializedName("photo")
    lateinit var galleryItems: List<GalleryItem>
}