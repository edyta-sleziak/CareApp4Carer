package org.wit.careapp4carer.models

import androidx.lifecycle.MutableLiveData

interface HrStore {
    fun getHrHistory() : MutableLiveData<ArrayList<HrModel>>
}