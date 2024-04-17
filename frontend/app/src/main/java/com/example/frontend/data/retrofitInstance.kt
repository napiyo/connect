package com.example.frontend.data

import android.app.Application
import android.content.Context
import android.util.Log
import com.example.frontend.MainActivity
import com.example.frontend.utils.configs
import com.example.frontend.utils.getToken
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AuthInterceptor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val authToken = getToken()
        // Add authorization header with the token
        val modifiedRequest = originalRequest.newBuilder()
            .header("Cookie", "token=$authToken")
            .build()

        return chain.proceed(modifiedRequest)
    }
}
object retrofitInstance {
    private val retrofit = Retrofit.Builder()
        .baseUrl(configs.apiBaseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(
            OkHttpClient.Builder()
                .addInterceptor(AuthInterceptor())
                .build()
        )
        .build()

    val api: ApiClient by lazy { retrofit.create(ApiClient::class.java) }
}