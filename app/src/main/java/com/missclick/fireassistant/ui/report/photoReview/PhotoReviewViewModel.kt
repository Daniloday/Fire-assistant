package com.missclick.fireassistant.ui.report.photoReview

import androidx.lifecycle.ViewModel
import com.missclick.fireassistant.data.models.FireReportModel
import com.missclick.fireassistant.data.remote.FireBaseDB
import com.missclick.fireassistant.data.repository.IRepository
import com.missclick.fireassistant.data.repository.Repository

class PhotoReviewViewModel : ViewModel() {

    fun fireReport(fireReportModel: FireReportModel){
        Repository(FireBaseDB()).fireReport(fireReportModel = fireReportModel)
    }

}