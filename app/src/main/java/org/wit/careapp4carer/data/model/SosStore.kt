package org.wit.careapp.models

import androidx.lifecycle.MutableLiveData

interface SosStore {
    fun getData(id: String): MutableLiveData<SosModel>
}