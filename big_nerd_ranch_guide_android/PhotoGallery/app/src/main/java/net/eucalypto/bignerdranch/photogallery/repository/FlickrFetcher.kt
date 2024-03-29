package net.eucalypto.bignerdranch.photogallery.repository

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import net.eucalypto.bignerdranch.photogallery.repository.api.FlickrApi
import net.eucalypto.bignerdranch.photogallery.repository.api.FlickrResponse
import net.eucalypto.bignerdranch.photogallery.repository.api.PhotoInterceptor
import net.eucalypto.bignerdranch.photogallery.model.GalleryItem
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

class FlickrFetcher {
    private val flickrApi: FlickrApi

    init {
        val client =
            OkHttpClient.Builder()
                .addInterceptor(PhotoInterceptor())
                .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.flickr.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
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

    fun fetchInterestingPhotosRequest(): Call<FlickrResponse> {
        return flickrApi.fetchInterestingPhotos()
    }

    fun fetchInterestingPhotos(): LiveData<List<GalleryItem>> {
        return fetchPhotoMetadata(fetchInterestingPhotosRequest())
    }

    fun fetchSearchPhotosRequest(query: String): Call<FlickrResponse> {
        return flickrApi.searchPhotos(query)
    }

    fun fetchSearchPhotos(query: String): LiveData<List<GalleryItem>> {
        return fetchPhotoMetadata(fetchSearchPhotosRequest(query))
    }

    private fun fetchPhotoMetadata(flickrRequest: Call<FlickrResponse>)
            : LiveData<List<GalleryItem>> {
        val responseLiveData = MutableLiveData<List<GalleryItem>>()

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