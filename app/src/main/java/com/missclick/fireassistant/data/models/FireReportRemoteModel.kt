package com.missclick.fireassistant.data.models

data class FireReportRemoteModel(
        var phoneNumber: String = "102",
        var photo: String? = "",
        var azimuth: Float? = 0.0f,
        var longitude: Double? = 0.0,
        var latitude: Double? = 0.0,
        var description: String? = ""
)

data class AllFireReports(val reports : MutableList<FireReportRemoteModel>? = mutableListOf())

