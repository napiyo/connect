package com.example.frontend.data.repoImpl

import com.example.frontend.data.model.ApiResponse
import com.example.frontend.data.model.ServerConfig
import com.example.frontend.data.repo.ServerConfigRepo
import com.example.frontend.data.retrofitInstance
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ServerConfigRepoImpl: ServerConfigRepo {
    override suspend fun getServerConfig(): Flow<ApiResponse<*>> {
        return flow {
            val res = ApiCallHandler.handleApiCall { retrofitInstance.api.getServerConfig() }
            emit(res)
        }
    }

    override suspend fun getVillages(): Flow<ApiResponse<*>> {
        return flow {
            val res = ApiCallHandler.handleApiCall { retrofitInstance.api.getVillages() }
            emit(res)
        }
    }
}