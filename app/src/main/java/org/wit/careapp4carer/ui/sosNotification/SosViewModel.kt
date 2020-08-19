package org.wit.careapp4carer.ui.sosNotification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.wit.careapp.models.SosModel
import org.wit.careapp4carer.models.AccountInfoModel
import org.wit.careapp4carer.models.LocationModel
import org.wit.careapp4carer.models.firebase.AccountInfoFireStore
import org.wit.careapp4carer.models.firebase.LocationFireStore
import org.wit.careapp4carer.models.firebase.SosFireStore

class SosViewModel : ViewModel() {

    private val sosFireStore = SosFireStore()
    private val locationFireStore = LocationFireStore()
    private var alertId = ""
    val mData: LiveData<SosModel> get() = sosFireStore.getData(alertId)
    val location : LiveData<LocationModel> = locationFireStore.getLatestLocation()



    fun getData(id: String): LiveData<SosModel> {
        alertId = id
        return mData
    }

    fun getLatestLocation(): LiveData<LocationModel> {
        return location
    }

}