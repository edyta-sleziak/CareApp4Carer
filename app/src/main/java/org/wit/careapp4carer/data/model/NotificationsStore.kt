package org.wit.careapp4carer.models

import androidx.lifecycle.MutableLiveData

interface NotificationsStore {
    fun editNotification(notification: NotificationsModel)
    fun getCompletedNotification(): MutableLiveData<ArrayList<NotificationsModel>>
    fun getActiveNotification(): MutableLiveData<ArrayList<NotificationsModel>>
    fun addNewNotification(notification: NotificationsModel)
    fun removeNotification(notificationId: String)
}