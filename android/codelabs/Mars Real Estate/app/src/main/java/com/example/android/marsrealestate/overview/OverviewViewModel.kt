/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.example.android.marsrealestate.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.marsrealestate.network.MarsApi
import com.example.android.marsrealestate.network.MarsApiFilter
import com.example.android.marsrealestate.network.MarsProperty
import kotlinx.coroutines.launch
import timber.log.Timber
import java.net.UnknownHostException

/**
 * The [ViewModel] that is attached to the [OverviewFragment].
 */
class OverviewViewModel : ViewModel() {

    private val _marsProperties = MutableLiveData<List<MarsProperty>>()
    val marsProperties: LiveData<List<MarsProperty>>
        get() = _marsProperties

    private val _networkStatus = MutableLiveData<NetworkStatus>()
    val networkStatus: LiveData<NetworkStatus>
        get() = _networkStatus


    private val _navigateToDetail = MutableLiveData<MarsProperty>()
    val navigateToDetail: LiveData<MarsProperty>
        get() = _navigateToDetail

    fun navigateToDetailStart(marsProperty: MarsProperty) {
        _navigateToDetail.value = marsProperty
    }

    fun navigateToDetailDone() {
        _navigateToDetail.value = null
    }

    /**
     * Call getMarsRealEstateProperties() on init so we can display status immediately.
     */
    init {
        getMarsRealEstateProperties(MarsApiFilter.SHOW_ALL)
    }

    /**
     * Sets the value of the networkStatus LiveData to the Mars API networkStatus.
     */
    private fun getMarsRealEstateProperties(
        filter: MarsApiFilter
    ) = viewModelScope.launch {

        try {
            _networkStatus.value = NetworkStatus.LOADING
            val properties = MarsApi.retrofitService
                .getPropertiesAsync(filter.value).await()

            _marsProperties.value = properties
            Timber.d("Success ${properties.size} Mars properties found")
            _networkStatus.value = NetworkStatus.DONE
        } catch (e: UnknownHostException) {
            Timber.d(e, "Error")
            _networkStatus.value = NetworkStatus.ERROR
        }
    }


    fun updateFilter(filter: MarsApiFilter) {
        getMarsRealEstateProperties(filter)
    }
}

enum class NetworkStatus {
    LOADING, ERROR, DONE
}