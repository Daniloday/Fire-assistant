package com.missclick.fireassistant.data.repository

import com.missclick.fireassistant.data.models.FireReportModel


interface IRepository {
    fun fireReport(fireReport: FireReportModel)
}