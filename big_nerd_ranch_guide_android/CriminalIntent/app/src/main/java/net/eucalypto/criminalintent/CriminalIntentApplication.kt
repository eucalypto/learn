package net.eucalypto.criminalintent

import android.app.Application
import timber.log.Timber

class CriminalIntentApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        CrimeRepository.initialize(this)
        Timber.plant(Timber.DebugTree())
    }
}