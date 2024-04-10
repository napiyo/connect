package com.example.frontend.data.repo

import com.example.frontend.data.model.ApiResponse
import kotlinx.coroutines.flow.Flow

interface ServerConfigRepo {
    suspend fun getServerConfig(): Flow<ApiResponse<String>>
}