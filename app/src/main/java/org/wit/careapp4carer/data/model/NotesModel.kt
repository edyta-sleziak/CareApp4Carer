package org.wit.careapp4carer.models
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.time.LocalDateTime

@Parcelize
data class NotesModel(
    var id: String = "",
    var title: String = "",
    var note: String = "",
    var updatedDate: String = "",
    var updatedBy: String = "",
    var isActive: Boolean = true
) : Parcelable

