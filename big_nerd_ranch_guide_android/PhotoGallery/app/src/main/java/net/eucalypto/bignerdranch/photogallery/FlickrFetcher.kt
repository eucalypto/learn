package net.eucalypto.bignerdranch.photogallery

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import net.eucalypto.bignerdranch.photogallery.api.FlickrApi
import net.eucalypto.bignerdranch.photogallery.api.FlickrResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

class FlickrFetcher {
    private val flickrApi: FlickrApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.flickr.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        flickrApi = retrofit.create(FlickrApi::class.java)
    }


    @WorkerThread
    fun fetchPhoto(url: String): Bitmap? {
        val response = flickrApi.fetchUrlBytes(url).execute()
        val bitmap = response.body()?.byteStream()?.use {
            BitmapFactory.decodeStream(it)
        }
        Timber.d("Decoded bitmap=$bitmap from Response=$response")
        return bitmap
    }

    fun fetchPhotos(): LiveData<List<GalleryItem>> {
        val responseLiveData = MutableLiveData<List<GalleryItem>>()
        val flickrRequest = flickrApi.fetchPhotos()

        flickrRequest.enqueue(object : Callback<FlickrResponse> {
            override fun onResponse(
                call: Call<FlickrResponse>, response: Response<FlickrResponse>
            ) {
                var galleryItems =
                    response.body()?.photos?.galleryItems ?: mutableListOf()
                galleryItems = galleryItems.filterNot {
                    it.url.isBlank()
                }
                responseLiveData.value = galleryItems
            }

            override fun onFailure(call: Call<FlickrResponse>, t: Throwable) {
                Timber.e(t, "Failed to fetch photos")
            }
        })

        return responseLiveData
    }
}