package com.example.frontend.data.repo

import com.example.frontend.data.model.ApiResponse
import com.example.frontend.data.model.ContactBody
import com.example.frontend.data.model.SendOtpBody
import com.example.frontend.data.model.phoneNumberClass
import kotlinx.coroutines.flow.Flow

interface AuthRepo {
    suspend fun sendOtp(body: phoneNumberClass): Flow<ApiResponse<String>>
    suspend fun verifyOTP(reqBody: SendOtpBody): Flow<ApiResponse<String>>
    suspend fun loginApi(reqBody: ContactBody): Flow<ApiResponse<*>>
    suspend fun logoutApi(): Flow<ApiResponse<String>>
}