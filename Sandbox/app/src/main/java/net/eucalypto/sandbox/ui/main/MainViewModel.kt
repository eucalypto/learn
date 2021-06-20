package net.eucalypto.sandbox.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class MainViewModel : ViewModel() {

    fun testCoroutineScopes() = viewModelScope.launch {
        Timber.d("debug: Launched viewModelScope on thread: ${Thread.currentThread().name}")

        withContext(Dispatchers.IO) {
            Timber.d("launched withContext(Dispatchers.IO): ${Thread.currentThread().name}")
        }
    }

}