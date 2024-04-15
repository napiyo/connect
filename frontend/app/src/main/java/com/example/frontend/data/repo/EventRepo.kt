package com.example.frontend.data.repo

import com.example.frontend.data.model.ApiResponse
import kotlinx.coroutines.flow.Flow

interface EventRepo {
    suspend fun getEvent(village:String?=null, page:String="0"): Flow<ApiResponse<*>>
}