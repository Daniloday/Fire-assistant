package com.missclick.fireassistant.data.remote

data class FireReportDB(
        val phoneNumber: String = "102",
        val photo: String,
        val azimuth: Float,
        val longitude: Double,
        val latitude: Double,
        val description: String
)