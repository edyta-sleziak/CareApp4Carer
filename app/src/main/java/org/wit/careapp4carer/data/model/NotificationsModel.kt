package org.wit.careapp4carer.models

import java.time.LocalDateTime

data class NotificationsModel(
    var id: String = "",
    var notification: String = "",
    var displayTime: LocalDateTime,
    var completedTime: LocalDateTime
)