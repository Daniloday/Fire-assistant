package com.missclick.fireassistant.data.repository


import android.util.Log
import com.missclick.fireassistant.data.models.FireReportModel
import com.missclick.fireassistant.data.remote.FireBaseDB
import com.missclick.fireassistant.data.remote.FirebaseStore
import java.util.*

class Repository : IRepository {

    val fireBaseDB = FireBaseDB()
    val fireBaseStore = FirebaseStore()

    override fun fireReport(fireReportModel: FireReportModel) {
        val photoId = UUID.randomUUID().toString()
        fireBaseStore.uploadImage("testUserId", photoId, fireReportModel.photo)
        val fireReportDB = mapFireReportModelToDB(fireReport = fireReportModel)
        Log.e("rep",fireReportModel.toString())
        fireBaseDB.fireReport(fireReportRemoteModel = fireReportDB)
    }
}