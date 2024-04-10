package com.example.frontend.data.model

data class ApiResponse<T>(
    val success: Boolean=false,
    val `data`:T? = null
)