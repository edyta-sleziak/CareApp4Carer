package org.wit.careapp4carer.models

interface NotificationsStore {
    fun displayNotification(notificationId: String)
    fun addNewNotification(notification: NotificationsModel)
    fun removeNotification(notificationId: String)
}