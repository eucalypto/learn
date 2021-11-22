package com.example.android.marsrealestate

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class MarsRealEstateApplication : Application() {

    private val applicationScope = CoroutineScope(Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()

        delayedInit()
    }

    private fun delayedInit() = applicationScope.launch {
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }
}