package org.wit.careapp4carer.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TodoModel(
    var id: String = "",
    var task: String = "",
    var isCompleted: Boolean = false,
    var dateCompleted: String = "",
    var notificationExists: Boolean = false
) : Parcelable