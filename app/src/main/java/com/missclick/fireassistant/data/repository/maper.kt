package com.missclick.fireassistant.data.repository

import com.missclick.fireassistant.data.models.FireReportModel
import com.missclick.fireassistant.data.remote.FireReportDB

fun mapFireReportModelToDB(fireReport: FireReportModel) : FireReportDB{
    return FireReportDB(
        phoneNumber = fireReport.phoneNumber,
        photo = fireReport.photo,
        azimuth = fireReport.azimuth,
        latitude = fireReport.latitude,
        longitude = fireReport.longitude,
        description = fireReport.description
    )
}