package com.missclick.fireassistant.ui.list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.missclick.fireassistant.data.remote.FireBaseDB
import com.missclick.fireassistant.data.remote.states.FirebaseState
import com.missclick.fireassistant.data.repository.Repository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class ListViewModel : ViewModel(), CoroutineScope {

    private val job = Job()
    //val fireBaseDB = FireBaseDB()
    private val repository = Repository()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job


    fun getList(){
        launch {
            try {
                repository.getAllFireReports().collect{
                    when(it){
                        is FirebaseState.Loading ->{
                            Log.e("ListViewModel", "Loading")
                        }
                        is FirebaseState.Success -> {
                            Log.e("ListViewModel", "Success ${it.data.toString()}")
                        }
                        is FirebaseState.Failed -> {
                            Log.e("ListViewModel", "Failed ${it.message}")
                        }
                    }
                }
            } catch (e : Exception){
                Log.e("ListViewModel", "Error ${e.toString()}")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

//    fun getList(){
//        repository.fireBaseDB.dbRef.addListenerForSingleValueEvent(object : ValueEventListener{
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//
//            override fun onDataChange(snapshot: DataSnapshot) {
//                Log.e("onDataChange", snapshot.toString())
//                for(child in snapshot.children){
//                    val fireReport = child.value
//                    Log.e("Data", fireReport.toString())
//                }
//            }
//
//        })
//    }
}