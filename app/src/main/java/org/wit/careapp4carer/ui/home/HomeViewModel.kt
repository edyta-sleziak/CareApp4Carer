package org.wit.careapp4carer.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.wit.careapp4carer.models.*
import org.wit.careapp4carer.models.firebase.*

class HomeViewModel : ViewModel() {

    val notificationsFireStore = NotificationsFireStore()
    val notesFireStore = NotesFireStore()
    val todoFireStore = TodoFireStore()
    val locationFireStore = LocationFireStore()
    val hrFireStore = HrFireStore()
    val accountInfoFireStore = AccountInfoFireStore()

    val mActiveNotificationCount : LiveData<Int> = notificationsFireStore.getActiveNotificationCount()
    val mCompletedNotificationCount : LiveData<Int> = notificationsFireStore.getCOmpletedNotificationCount()
    val mNotesCount : LiveData<Int> = notesFireStore.getNumberOfNotes()
    val mToDoItemsCount : LiveData<Int> = todoFireStore.getNumberOfToDoItems()
    val location : LiveData<LocationModel> = locationFireStore.getLatestLocation()
    val data : LiveData<AccountInfoModel> = accountInfoFireStore.getData()
    val hr : LiveData<HrModel> = hrFireStore.getLatestHr()


    fun getActiveNotificationCount(): LiveData<Int> {
        return mActiveNotificationCount
    }

    fun getCompletedNotificationCount(): LiveData<Int> {
        return mCompletedNotificationCount
    }

    fun getNotesCount(): LiveData<Int> {
        return mNotesCount
    }

    fun getToDoItemsCount(): LiveData<Int> {
        return mToDoItemsCount
    }

    fun getLatestLocation(): LiveData<LocationModel> {
        return location
    }

    fun getLatestHrReading() : LiveData<HrModel> {
        return hr
    }

    fun getAccountData() : LiveData<AccountInfoModel> {
        return data
    }
}
