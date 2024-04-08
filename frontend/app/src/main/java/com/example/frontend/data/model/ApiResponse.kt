package com.example.frontend.api

data class ApiResponse<T>(
    val success: Boolean=false,
    val `data`:T? = null
)