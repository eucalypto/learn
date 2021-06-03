package net.eucalypto.bignerdranch.photogallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import net.eucalypto.bignerdranch.photogallery.api.FlickrApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import timber.log.Timber

class FlickrFetcher {
    private val flickrApi: FlickrApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.flickr.com/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()

        flickrApi = retrofit.create(FlickrApi::class.java)
    }

    fun fetchPhotos(): LiveData<String> {
        val responseLiveData = MutableLiveData<String>()
        val flickrHomePageRequest = flickrApi.fetchPhotos()

        flickrHomePageRequest.enqueue(object : Callback<String> {
            override fun onResponse(
                call: Call<String>, response: Response<String>
            ) {
                responseLiveData.value = response.body()
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Timber.e(t, "Failed to fetch photos")
            }
        })

        return responseLiveData
    }
}