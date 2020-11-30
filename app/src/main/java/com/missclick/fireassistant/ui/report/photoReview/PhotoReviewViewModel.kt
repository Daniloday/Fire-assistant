package com.missclick.fireassistant.ui.report.photoReview

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.missclick.fireassistant.data.models.FireReportModel
import com.missclick.fireassistant.data.remote.FireBaseDB
import com.missclick.fireassistant.data.remote.FirebaseStore
import com.missclick.fireassistant.data.remote.states.FirebaseState
import com.missclick.fireassistant.data.repository.IRepository
import com.missclick.fireassistant.data.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class PhotoReviewViewModel : ViewModel() {

    val liveToastText = MutableLiveData<String>()

    fun fireReport(fireReportModel: FireReportModel){
        GlobalScope.launch(Dispatchers.IO) {
//            Repository().fireReport(fireReportModel = fireReportModel, callback = {
//                liveToastText.value = it
//            })
            try {
                Repository().fireReport(fireReportModel).collect{
                    when(it){
                        is FirebaseState.Loading ->{
                            Log.e("PreviewViewModel", "Loading")
                        }
                        is FirebaseState.Success -> {
                            withContext(Dispatchers.Main){ liveToastText.value = "Success" }
                        }
                        is FirebaseState.Failed -> {
                            withContext(Dispatchers.Main){ liveToastText.value = it.message }
                        }
                    }
                }
            } catch (e : Exception){
                Log.e("PreviewViewModel", e.toString())
            }

        }
    }

}