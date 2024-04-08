package com.example.frontend.data.repo

import com.example.frontend.data.model.ApiResponse
import com.example.frontend.data.retrofitInstance
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ServerConfigRepoImpl:ServerConfigRepo {
    override suspend fun getServerConfig(): Flow<ApiResponse<String>> {
        return flow {
            val res = ApiCallHandler.handleApiCall { retrofitInstance.api.getServerConfig()  }
            val convertedResponse = when(res.data) {
                is String -> ApiResponse(res.success, res.data as String)
                else -> ApiResponse(res.success, "Unexpected response type")
            }
            emit(convertedResponse)
        }
    }
}