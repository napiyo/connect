package com.example.frontend.data.repo

import com.example.frontend.data.model.ApiResponse
import com.google.gson.Gson
import java.io.IOException
import java.net.ConnectException
import java.net.UnknownHostException

object ApiCallHandler {
    suspend fun handleApiCall(apiCall: suspend () -> ApiResponse<*>): ApiResponse<*> {
        return try {
                apiCall()
            } catch (e: retrofit2.HttpException) {
                handleHttpException(e)
            } catch (e: UnknownHostException) {
                handleUnknownHostException(e)
            } catch (e: ConnectException) {
                handleConnectException(e)
            } catch (e: IOException) {
                handleIOException(e)
            } catch (e: Exception) {
                handleGenericException(e)
            }

    }

    private fun handleHttpException(e: retrofit2.HttpException): ApiResponse<String> {
        val errorBody = e.response()?.errorBody()?.string()
        val errorMessage = Gson().fromJson(errorBody, ApiResponse::class.java).data
        return ApiResponse<String>(false,"HTTP error: $errorMessage")
    }

    private fun handleUnknownHostException(e: UnknownHostException): ApiResponse<String> {
        e.printStackTrace()
        return ApiResponse<String>(false,"Unknown host: ${e.message}")
    }

    private fun handleConnectException(e: ConnectException): ApiResponse<String> {
        e.printStackTrace()
        return ApiResponse<String>(false,"Connection refused: ${e.message}")
    }

    private fun handleIOException(e: IOException): ApiResponse<String> {
        e.printStackTrace()
        return ApiResponse<String>(false,"IO error: ${e.message}")
    }

    private fun handleGenericException(e: Exception): ApiResponse<String> {
        e.printStackTrace()
        return ApiResponse<String>(false,"Exception occurred: ${e.message}")
    }
}