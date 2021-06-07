package net.eucalypto.bignerdranch.photogallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class PhotoGalleryViewModel : ViewModel() {

    val galleryItemLiveData: LiveData<List<GalleryItem>> =
        FlickrFetcher().searchPhotos("llama")

}