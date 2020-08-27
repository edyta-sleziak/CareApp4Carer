package org.wit.careapp4carer.models

import androidx.lifecycle.MutableLiveData

interface AccountInfoStore {
    fun getData() : MutableLiveData<AccountInfoModel>
    fun add(newAccount: AccountInfoModel)
    fun addToken(token: String)
    fun getUser(): String?
    fun updateAccountInDb(accountName: String, carerName: String, patientName: String)
    fun updateApplicationInDb(sosContactNumber: String,saveHrRangeLow: String, saveHrRangeHigh: String,saveHomeDistance: String,dailyStepsGoal: String,notificationResponseTime: String)
    fun saveHomeLocation(location: LocationModel)
}