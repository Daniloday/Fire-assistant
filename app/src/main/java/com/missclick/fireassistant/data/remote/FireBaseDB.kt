package com.missclick.fireassistant.data.remote

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.missclick.fireassistant.data.models.FireReportRemoteModel
import com.missclick.fireassistant.data.remote.states.FirebaseState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await

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
            val reportRef = dbRef.child(idLog).setValue(fireReportRemoteModel)
                .isSuccessful
            emit(FirebaseState.success(reportRef))
        }
    }.catch {
        emit(FirebaseState.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)

    fun getFireReports(){

    }

}