package com.example.frontend.data.repo

import com.example.frontend.data.model.ApiResponse
import com.example.frontend.data.model.ContactBody
import com.example.frontend.data.model.SendOtpBody
import com.example.frontend.data.retrofitInstance
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AuthRepoImpl:AuthRepo {
    override suspend fun sendOtp(phoneNumber: Int): Flow<ApiResponse<String>> {
        return flow {
            val res = ApiCallHandler.handleApiCall { retrofitInstance.api.sendOtp(phoneNumber)  }
            val convertedResponse = when(res.data) {
                is String -> ApiResponse(res.success, res.data as String)
                else -> ApiResponse(res.success, "Unexpected response type")
            }
            emit(convertedResponse)
        }
    }

    override suspend fun verifyOTP(reqBody: SendOtpBody): Flow<ApiResponse<String>> {
        return flow {
            val res = ApiCallHandler.handleApiCall { retrofitInstance.api.verifyOTP(reqBody)  }
            val convertedResponse = when(res.data) {
                is String -> ApiResponse(res.success, res.data as String)
                else -> ApiResponse(res.success, "Unexpected response type")
            }
            emit(convertedResponse)
        }
    }

    override suspend fun loginApi(reqBody: ContactBody): Flow<ApiResponse<*>> {
        return flow {
            val res = ApiCallHandler.handleApiCall { retrofitInstance.api.loginApi(reqBody)  }
            emit(res)
        }
    }

    override suspend fun logoutApi(): Flow<ApiResponse<String>> {
        return flow {
            val res = ApiCallHandler.handleApiCall { retrofitInstance.api.logoutApi()  }
            val convertedResponse = when(res.data) {
                is String -> ApiResponse(res.success, res.data as String)
                else -> ApiResponse(res.success, "Unexpected response type")
            }
            emit(convertedResponse)
        }
    }
}