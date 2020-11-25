package com.missclick.fireassistant.data.remote

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.missclick.fireassistant.data.models.FireReportRemoteModel

class FireBaseDB {

    var dbRef : DatabaseReference

    init {
        val db = FirebaseDatabase.getInstance()
//        dbRef = db.getReference("FireSpots")
        dbRef = db.getReference("FireSpots")
    }

    fun fireReport(fireReportRemoteModel: FireReportRemoteModel) {
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
            dbRef.child(idLog).setValue(fireReportRemoteModel)
        }

    }

    fun getFireReports(){

    }

}