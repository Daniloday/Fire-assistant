package com.missclick.fireassistant.data.repository

import com.missclick.fireassistant.data.models.FireReportModel
import com.missclick.fireassistant.data.models.FireReportRemoteModel
import java.util.*

fun mapFireReportModelToDB(fireReport: FireReportModel, photoId : String) : FireReportRemoteModel {
    return FireReportRemoteModel(
        phoneNumber = fireReport.phoneNumber,
        photo = photoId,
        azimuth = fireReport.azimuth,
        latitude = fireReport.latitude,
        longitude = fireReport.longitude,
        description = fireReport.description
    )
}

fun mapDBtoFireRepordModel(fireReportRemoteModel: FireReportRemoteModel, photo : ByteArray) : FireReportModel{
    return FireReportModel(
        phoneNumber = fireReportRemoteModel.phoneNumber,
        photo = photo,
        azimuth = fireReportRemoteModel.azimuth!!,
        latitude = fireReportRemoteModel.latitude!!,
        longitude = fireReportRemoteModel.longitude!!,
        description = fireReportRemoteModel.description!!
    )
}