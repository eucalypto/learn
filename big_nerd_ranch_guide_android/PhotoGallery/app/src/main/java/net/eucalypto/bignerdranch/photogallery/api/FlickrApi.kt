package net.eucalypto.bignerdranch.photogallery.api

import net.eucalypto.bignerdranch.photogallery.BuildConfig
import retrofit2.Call
import retrofit2.http.GET

private const val FLICKR_API_KEY = BuildConfig.FLICKR_API_KEY

interface FlickrApi {

    @GET(
        "services/rest/?method=flickr.interestingness.getList" +
                "&api_key=$FLICKR_API_KEY" +
                "&format=json" +
                "&nojsoncallback=1" +
                "&extras=url_s"
    )
    fun fetchPhotos(): Call<FlickrResponse>

}