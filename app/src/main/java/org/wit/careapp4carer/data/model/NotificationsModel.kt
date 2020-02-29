package org.wit.careapp4carer.models

import java.time.LocalDateTime

data class NotificationsModel(
    var id: Long = 0,
    var notification: String = "",
    var displayTime: LocalDateTime,
    var completedTime: LocalDateTime,
    var userId: Long = 0
)