package com.missclick.fireassistant.data.models

import com.missclick.fireassistant.domain.Coordinate

data class FireModel (
    val coordinate : Coordinate,
    val reports: List<FireReportModel>
                      )