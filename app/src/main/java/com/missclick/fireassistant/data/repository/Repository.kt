package com.missclick.fireassistant.data.repository


import android.util.Log
import com.missclick.fireassistant.data.models.FireReportModel
import com.missclick.fireassistant.data.models.FireReportRemoteModel
import com.missclick.fireassistant.data.remote.FireBaseDB
import com.missclick.fireassistant.data.remote.FirebaseStore
import com.missclick.fireassistant.data.remote.states.FirebaseState
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import java.lang.Exception
import java.util.*

class Repository : IRepository {

    val fireBaseDB = FireBaseDB()
    val fireBaseStore = FirebaseStore()

//    override fun fireReport(fireReportModel: FireReportModel, callback : (String) -> (Unit)) {
//        val photoId = UUID.randomUUID().toString()
//        fireBaseStore.uploadImage("testUserId", photoId, fireReportModel.photo)
//        val fireReportDB = mapFireReportModelToDB(fireReport = fireReportModel, photoId = photoId)
//        Log.e("rep",fireReportModel.toString())
//        fireBaseDB.fireReport(fireReportRemoteModel = fireReportDB, callback = callback)
//    }

    override suspend fun fireReport(fireReport: FireReportModel) : Flow<FirebaseState<Boolean>> {
        val photoId = UUID.randomUUID().toString()
        val fireReportDB = mapFireReportModelToDB(fireReport = fireReport, photoId = photoId)
        Log.e("rep",fireReport.toString())
        val a = fireBaseStore.uploadImageSuspend("testUserId", photoId, fireReport.photo)
        if(a){
            Log.e("FireReport", "Success image, upload DB")
            return fireBaseDB.fireReportSuspend(fireReportRemoteModel = fireReportDB)
        }
        throw Exception()
    }

    override suspend fun getAllFireReports(): Flow<FirebaseState<FireReportModel>> {
        return fireBaseDB.getAllFireReports(userId = "testUserId", fireStore = fireBaseStore)
    }


}