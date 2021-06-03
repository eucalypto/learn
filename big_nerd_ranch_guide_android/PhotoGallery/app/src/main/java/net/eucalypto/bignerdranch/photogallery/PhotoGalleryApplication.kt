package net.eucalypto.bignerdranch.photogallery

import android.app.Application
import timber.log.Timber

class PhotoGalleryApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }
}