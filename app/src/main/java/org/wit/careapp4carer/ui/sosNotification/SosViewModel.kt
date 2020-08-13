package org.wit.careapp4carer.ui.sosNotification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.wit.careapp.models.SosModel
import org.wit.careapp4carer.models.AccountInfoModel
import org.wit.careapp4carer.models.firebase.AccountInfoFireStore
import org.wit.careapp4carer.models.firebase.SosFireStore

class SosViewModel : ViewModel() {

    private val sosFireStore = SosFireStore()
    private var alertId = ""
    val mData: LiveData<SosModel> get() = sosFireStore.getData(alertId)



    fun getData(id: String): LiveData<SosModel> {
        alertId = id
        return mData
    }

}