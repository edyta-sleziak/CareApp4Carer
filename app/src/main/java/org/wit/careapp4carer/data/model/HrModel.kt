package org.wit.careapp4carer.models
import java.time.LocalDateTime

data class HrModel(
    var id: Long = 0,
    var hrValue: Int = 0,
    var dateTime: LocalDateTime = LocalDateTime.now(),
    var userId: Long = 0
)