package net.eucalypto.bignerdranch.photogallery.gallery

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import net.eucalypto.bignerdranch.photogallery.model.GalleryItem
import net.eucalypto.bignerdranch.photogallery.repository.FlickrFetcher
import net.eucalypto.bignerdranch.photogallery.repository.QueryPreferences

class PhotoGalleryViewModel(private val app: Application) :
    AndroidViewModel(app) {

    private val flickrFetcher = FlickrFetcher()
    private val mutableSearchTerm = MutableLiveData<String>()

    val searchTerm: String
        get() = mutableSearchTerm.value ?: ""

    init {
        mutableSearchTerm.value = QueryPreferences.getStoredQuery(app)
    }


    val galleryItemLiveData: LiveData<List<GalleryItem>> =
        Transformations.switchMap(mutableSearchTerm) { searchTerm ->
            if (searchTerm.isBlank()) {
                flickrFetcher.fetchInterestingPhotos()
            } else {
                flickrFetcher.fetchSearchPhotos(searchTerm)
            }
        }

    fun fetchPhotos(query: String = "") {
        QueryPreferences.setStoredQuery(app, query)
        mutableSearchTerm.value = query
    }

}