package net.eucalypto.bignerdranch.photogallery

import android.graphics.Bitmap
import android.os.Handler
import android.os.HandlerThread
import android.os.Message
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import timber.log.Timber
import java.util.concurrent.ConcurrentHashMap

private const val MESSAGE_DOWNLOAD = 0


class ThumbnailDownloader<in T>(
    private val responseHandler: Handler,
    private val onThumbnailDownloader: (T, Bitmap) -> Unit
) : HandlerThread("ThumbnailDownloader") {

    private var hasQuit = false
    private lateinit var requestHandler: Handler
    private val requestMap = ConcurrentHashMap<T, String>()
    private val flickrFetcher = FlickrFetcher()


    val fragmentLifecycleObserver = object : LifecycleObserver {
        @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
        fun setup() {
            Timber.i("Starting background thread")
            start()
            looper
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        fun tearDown() {
            Timber.i("Destroying background thread")
            quit()
        }
    }

    val viewLifecycleObserver = object : LifecycleObserver {
        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        fun clearQueue() {
            Timber.i("Clearing all requests from queue")
            requestHandler.removeMessages(MESSAGE_DOWNLOAD)
            requestMap.clear()
        }
    }


    override fun quit(): Boolean {
        hasQuit = true
        return super.quit()
    }


    fun queueThumbnail(target: T, url: String) {
        Timber.i("Got a URL: $url")
        requestMap[target] = url
        requestHandler.obtainMessage(MESSAGE_DOWNLOAD, target).sendToTarget()
    }

    override fun onLooperPrepared() {
        requestHandler = object : Handler() {
            override fun handleMessage(msg: Message) {
                if (msg.what == MESSAGE_DOWNLOAD) {
                    val target = msg.obj as T
                    Timber.i("Got a request for URL: ${requestMap[target]}")
                    handleRequest(target)
                }
            }
        }
    }

    private fun handleRequest(target: T) {
        val url = requestMap[target] ?: return
        val bitmap = flickrFetcher.fetchPhoto(url) ?: return

        responseHandler.post {
            if (requestMap[target] != url || hasQuit) return@post

            requestMap.remove(target)
            onThumbnailDownloader(target, bitmap)
        }
    }
}