package net.eucalypto.bignerdranch.draganddraw

import android.app.Application
import timber.log.Timber

class DragAndDrawApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }
}