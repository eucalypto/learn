package net.eucalypto.bignerdranch.beatbox

import android.app.Application
import timber.log.Timber
import timber.log.Timber.DebugTree

class BeatBoxApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(DebugTree())
    }
}