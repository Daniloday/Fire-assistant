package com.missclick.fireassistant.ui.myreports

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.missclick.fireassistant.data.models.FireReportModel
import com.missclick.fireassistant.data.remote.states.FirebaseState
import com.missclick.fireassistant.data.repository.Repository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class MyReportsViewModel : ViewModel(), CoroutineScope {
    // TODO: Implement the ViewModel

    private val job = Job()
    //val fireBaseDB = FireBaseDB()
    private val repository = Repository()

    val myReports = MutableLiveData<FireReportModel>()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    fun getMyReporst(){
        launch(Dispatchers.IO) {
            try {
                repository.getAllFireReports().collect{
                    when(it){
                        is FirebaseState.Loading ->{
                            Log.e("MyReportsViewModel", "Loading")
                        }
                        is FirebaseState.Success -> {
                            Log.e("MyReportsViewModel", "Success ${it.data.toString()}")
                            withContext(Dispatchers.Main) { myReports.value = it.data }
                        }
                        is FirebaseState.Failed -> {
                            Log.e("MyReportsViewModel", "Failed ${it.message}")
                        }
                    }
                }
            } catch (e : Exception){
                Log.e("MyReportsViewModel", "Error ${e.toString()}")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

}