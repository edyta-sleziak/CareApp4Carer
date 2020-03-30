package org.wit.careapp4carer.models

data class NotificationsModel(
    var id: String = "",
    var notification: String = "",
    var displayTime: String = "",
    var completedTime: String? = null
)