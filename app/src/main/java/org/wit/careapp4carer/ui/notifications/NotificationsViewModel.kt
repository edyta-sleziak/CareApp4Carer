package org.wit.careapp4carer.ui.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.wit.careapp4carer.models.NotificationsModel
import org.wit.careapp4carer.models.firebase.NotificationsFireStore

class NotificationsViewModel : ViewModel() {

    val notificationsFireStore = NotificationsFireStore()
    val mNotificationsList: LiveData<ArrayList<NotificationsModel>> get() = notificationsFireStore.getMutalbleLiveData()

    fun getNotificationsList(): LiveData<ArrayList<NotificationsModel>> {
        return mNotificationsList
    }

}