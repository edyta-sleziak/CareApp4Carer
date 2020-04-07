package org.wit.careapp4carer.models

interface NotificationsStore {
    fun getAll(): ArrayList<NotificationsModel>
    fun displayNotification(noteId: Long)
    fun markAsDone(noteId: Long)
    fun displayLater(noteId: Long)
    fun addNewNotification(notification: NotificationsModel)
    fun removeNotification(notificationId: String)
}