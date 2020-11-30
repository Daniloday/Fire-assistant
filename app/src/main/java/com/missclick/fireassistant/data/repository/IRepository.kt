package com.missclick.fireassistant.data.repository

import com.missclick.fireassistant.data.models.FireReportModel
import com.missclick.fireassistant.data.remote.states.FirebaseState
import kotlinx.coroutines.flow.Flow


interface IRepository {
    suspend fun fireReport(fireReport: FireReportModel) : Flow<FirebaseState<Boolean>>
}