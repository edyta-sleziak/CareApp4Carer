package org.wit.careapp4carer.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.wit.careapp4carer.models.NotificationsModel
import org.wit.careapp4carer.models.firebase.NotificationsFireStore

class HomeViewModel : ViewModel() {

    val notificationsFireStore = NotificationsFireStore()
    val mItemsCount : Int = notificationsFireStore.getActiveNotificationCount()
    val mCompletedItemsCount = notificationsFireStore.getCOmpletedNotificationCount()


    fun getActiveNotificationCount(): Int {
        return mItemsCount
    }
}
