package org.wit.careapp4carer.models

data class LocationModel(
    var latitude:  Double = 0.0,
    var longitude: Double = 0.0,
    var zoom: Float = 0f,
    var dateAndTime: String = ""
)