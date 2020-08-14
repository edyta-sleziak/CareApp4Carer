package org.wit.careapp4carer.models

import androidx.lifecycle.MutableLiveData

interface LocationStore {
    fun getLocationHistory(): MutableLiveData<ArrayList<LocationModel>>
    fun add(location: LocationModel)
}