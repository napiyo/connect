package com.example.frontend.data

sealed class ApiResponse<T>(
    val success: Boolean,
    val `data`:T? = null,
    val errMessage:String? = null
){
    class Success<T> (data:T?): ApiResponse<T>(true,data=data)
    class Error (errMessage: String?): ApiResponse<String>(false,errMessage=errMessage)
}