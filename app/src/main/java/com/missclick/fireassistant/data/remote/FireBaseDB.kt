package com.missclick.fireassistant.data.remote

import android.util.Log
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class FireBaseDB {

    var dbRef : DatabaseReference

    init {
        val db = FirebaseDatabase.getInstance()
//        dbRef = db.getReference("FireSpots")
        dbRef = db.getReference("FireSpots")
    }

    fun fireReport(fireReportDB: FireReportDB) {
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
            dbRef.child(idLog).setValue(fireReportDB)
        }

    }

    fun getFireReports(){

    }

}