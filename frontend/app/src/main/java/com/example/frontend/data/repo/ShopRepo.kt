package com.example.frontend.data.repo

import com.example.frontend.data.model.ApiResponse
import kotlinx.coroutines.flow.Flow

interface ShopRepo {
    suspend fun getshops(village:String?=null, page:String="0"): Flow<ApiResponse<*>>
}