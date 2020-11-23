package com.missclick.fireassistant.data.repository


import com.missclick.fireassistant.data.models.FireReportModel
import com.missclick.fireassistant.data.remote.FireBaseDB

class Repository(val fireBaseDB : FireBaseDB) : IRepository {


    override fun fireReport(fireReportModel: FireReportModel) {
        val fireReportDB = mapFireReportModelToDB(fireReport = fireReportModel)
        fireBaseDB.fireReport(fireReportDB = fireReportDB)
    }
}