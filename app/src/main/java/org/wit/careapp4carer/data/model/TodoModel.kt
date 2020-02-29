package org.wit.careapp4carer.models

import java.time.LocalDateTime

data class TodoModel(
    var id: String = "",
    var task: String = "",
    var isCompleted: Boolean = false,
    var dateCompleted: LocalDateTime?,
    var userId: Long = 0
)