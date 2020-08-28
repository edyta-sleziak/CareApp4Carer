package org.wit.careapp4carer.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.wit.careapp4carer.models.AccountInfoModel
import org.wit.careapp4carer.models.firebase.AccountInfoFireStore

class SettingsViewModel : ViewModel() {

    private val settingFireStore = AccountInfoFireStore()
    val mData: LiveData<AccountInfoModel> get() = settingFireStore.getData()



    fun getDataFromDb(): LiveData<AccountInfoModel> {
        return mData
    }

    fun updateAccountInfo(accountName: String, carerName: String, patientName: String) {
        settingFireStore.updateAccountInDb(accountName,carerName,patientName)
    }

    fun updateAppSettings(sosContactNumber: String,saveHrRangeLow: String, saveHrRangeHigh: String,saveHomeDistance: String,dailyStepsGoal: String,notificationResponseTime: String) {
        settingFireStore.updateApplicationInDb(sosContactNumber,saveHrRangeLow,saveHrRangeHigh, saveHomeDistance,dailyStepsGoal,notificationResponseTime)
    }

}