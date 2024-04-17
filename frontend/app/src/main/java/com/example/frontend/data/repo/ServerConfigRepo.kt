package com.example.frontend.data.repo

import com.example.frontend.data.model.ApiResponse
import com.example.frontend.data.model.ServerConfig
import kotlinx.coroutines.flow.Flow

interface ServerConfigRepo {
    suspend fun getServerConfig(): Flow<ApiResponse<*>>
    suspend fun getVillages(): Flow<ApiResponse<*>>
}