package com.missclick.fireassistant.domain

import com.missclick.fireassistant.data.models.FireReportModel

data class Intersection (
    val coordinate: Coordinate,
    val firstReport: FireReportModel,
    val secondReport : FireReportModel
)