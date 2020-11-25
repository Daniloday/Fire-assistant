package com.missclick.fireassistant.data.repository


import android.util.Log
import com.missclick.fireassistant.data.models.FireReportModel
import com.missclick.fireassistant.data.remote.FireBaseDB

class Repository(val fireBaseDB : FireBaseDB) : IRepository {


    override fun fireReport(fireReportModel: FireReportModel) {
        val fireReportDB = mapFireReportModelToDB(fireReport = fireReportModel)
        Log.e("rep",fireReportModel.toString())
        fireBaseDB.fireReport(fireReportRemoteModel = fireReportDB)
    }
}