package net.eucalypto.bignerdranch.photogallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class PhotoGalleryViewModel : ViewModel() {

    private val flickrFetcher = FlickrFetcher()
    private val mutableSearchTerm = MutableLiveData<String>("dog")

    val galleryItemLiveData: LiveData<List<GalleryItem>> =
        Transformations.switchMap(mutableSearchTerm) { searchTerm ->
            flickrFetcher.searchPhotos(searchTerm)
        }

    fun fetchPhotos(query: String = "") {
        mutableSearchTerm.value = query
    }

}