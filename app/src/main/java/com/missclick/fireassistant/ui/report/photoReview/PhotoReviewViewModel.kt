package com.missclick.fireassistant.ui.report.photoReview

import androidx.lifecycle.ViewModel
import com.missclick.fireassistant.data.models.FireReportModel
import com.missclick.fireassistant.data.remote.FireBaseDB
import com.missclick.fireassistant.data.repository.IRepository
import com.missclick.fireassistant.data.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PhotoReviewViewModel : ViewModel() {

    fun fireReport(fireReportModel: FireReportModel){
        GlobalScope.launch(Dispatchers.IO) {
            Repository().fireReport(fireReportModel = fireReportModel)
        }
    }

}