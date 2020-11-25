package com.missclick.fireassistant.data.repository

import com.missclick.fireassistant.data.models.FireReportModel
import com.missclick.fireassistant.data.models.FireReportRemoteModel

fun mapFireReportModelToDB(fireReport: FireReportModel) : FireReportRemoteModel {
    return FireReportRemoteModel(
        phoneNumber = fireReport.phoneNumber,
        photo = fireReport.photo.toString(),
        azimuth = fireReport.azimuth,
        latitude = fireReport.latitude,
        longitude = fireReport.longitude,
        description = fireReport.description
    )
}