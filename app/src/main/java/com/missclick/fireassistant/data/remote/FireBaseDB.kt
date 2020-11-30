package com.missclick.fireassistant.data.remote

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.getValue
import com.missclick.fireassistant.data.models.AllFireReports
import com.missclick.fireassistant.data.models.FireReportModel
import com.missclick.fireassistant.data.models.FireReportRemoteModel
import com.missclick.fireassistant.data.remote.features.getValue
import com.missclick.fireassistant.data.remote.states.FirebaseState
import com.missclick.fireassistant.data.repository.mapDBtoFireRepordModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class FireBaseDB {

    var dbRef : DatabaseReference

    init {
        val db = FirebaseDatabase.getInstance()
//        dbRef = db.getReference("FireSpots")
        dbRef = db.getReference("FireSpots")
    }

    fun fireReport(fireReportRemoteModel: FireReportRemoteModel, callback : (String) -> (Unit)) {
//        return GlobalScope.async {
//            val idLog = dbRef.push().key
//            Log.e("db", idLog.toString())
//            if (idLog != null) {
//                dbRef.child(idLog).setValue(fireReportDB)
//                idLog
//            }
//            else null
//        }
//        dbRef.setValue("hello")

        val idLog = dbRef.push().key
        if (idLog != null) {
            dbRef.child(idLog).setValue(fireReportRemoteModel).addOnSuccessListener {
                callback.invoke("Success sending report")
            }
        }
    }

    fun fireReportSuspend(fireReportRemoteModel: FireReportRemoteModel) = flow<FirebaseState<Boolean>> {
        emit(FirebaseState.loading())
        val idLog = dbRef.push().key
        if (idLog != null) {
            val reportRef = dbRef.child(idLog).setValue(fireReportRemoteModel).isSuccessful
            emit(FirebaseState.success(reportRef))
        } else emit(FirebaseState.failed("Error"))
    }.catch {
        emit(FirebaseState.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)

    fun getAllFireReports(fireStore : FirebaseStore, userId : String) = flow<FirebaseState<FireReportModel>>{
        emit(FirebaseState.loading())
        val snapshot = dbRef.getValue().await()
        val data = getReportsForString(snapshots = snapshot)
        Log.e("FirebaseDB", "data : ${data.toString()}")

        for(report in data.reports!!){
            val image = report.photo?.let { fireStore.getImage(userId = userId, photoId = it) }
            Log.e("FirebaseDB", "image : ${image.toString()}, report : ${report.toString()}")
            if(image != null){
                try {
                    emit(FirebaseState.success(mapDBtoFireRepordModel(report, image)))
                } catch (e : Exception){
                    Log.e("FirebaseDB", e.toString())
                }
            }
            else emit(FirebaseState.failed("data or image are null"))
        }

    }.catch {
        emit(FirebaseState.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)


     private fun getReportsForString(snapshots : DataSnapshot) : AllFireReports{
        val reports = AllFireReports()
        for(snapshot in snapshots.children){
            //Log.e("Kek", "snapshot : ${snapshot.toString()} ")
            val model = snapshot.getValue<FireReportRemoteModel>()
            if (model != null) {
                reports.reports?.add(model)
            }

        }
        return reports
    }

}