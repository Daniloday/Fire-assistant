package com.missclick.fireassistant.data.models

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class FireReportModel(
        val phoneNumber : String = "102",
        val photo : ByteArray,
        val azimuth : Float,
        val longitude : Double,
        val latitude : Double,
        val description : String = ""
): Serializable