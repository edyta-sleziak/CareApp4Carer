package org.wit.careapp4carer.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AccountInfoModel(
    var email: String = "",
    var carerName: String = "",
    var epName: String = "",
    var sosContactNumber: String = "",
    var saveHrRangeHigh: String = "",
    var saveHrRangeLow: String = "",
    var saveHomeDistance: String = "",
    var dailyStepsGoal: String = "",
    var notificationResponseTime: String = "",
    var location: Location = Location()): Parcelable

@Parcelize
data class Location(var lat: Double = 0.0,
                    var lng: Double = 0.0,
                    var zoom: Float = 0f ) : Parcelable