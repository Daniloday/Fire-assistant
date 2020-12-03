package com.missclick.fireassistant.data.models

import com.missclick.fireassistant.domain.Coordinate
import java.io.Serializable

data class FireModel (
    val coordinate : Coordinate,
    val reports: List<FireReportModel>
                      ): Serializable