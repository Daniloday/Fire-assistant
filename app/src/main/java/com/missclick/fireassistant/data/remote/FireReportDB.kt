package com.missclick.fireassistant.data.remote

class FireReportDB(
    val phoneNumber : String = "102",
    val photo : ByteArray,
    val azimuth : Float,
    val longitude : Double,
    val latitude : Double,
    val description : String
)