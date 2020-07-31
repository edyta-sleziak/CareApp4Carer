package org.wit.careapp4carer.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NotificationsModel(
    var id: String = "",
    var userId: String = "",
    var notification: String = "",
    var displayDate: String = "",
    var displayTime: String = "",
    var completedTime: String = "Not completed"
) : Parcelable