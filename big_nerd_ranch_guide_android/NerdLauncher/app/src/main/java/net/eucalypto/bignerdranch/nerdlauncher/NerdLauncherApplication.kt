package net.eucalypto.bignerdranch.nerdlauncher

import android.app.Application
import timber.log.Timber

class NerdLauncherApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}