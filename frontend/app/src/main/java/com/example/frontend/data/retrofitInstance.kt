package com.example.frontend.api

import com.example.frontend.utils.configs
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object retrofitInstance {

    private val retrofit = Retrofit.Builder()
        .baseUrl(configs.apiBaseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api: ApiClient by lazy { retrofit.create(ApiClient::class.java) }
}