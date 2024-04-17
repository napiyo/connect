package com.example.frontend.data

import com.example.frontend.data.model.ApiResponse
import com.example.frontend.data.model.Contact
import com.example.frontend.data.model.ContactBody
import com.example.frontend.data.model.EventClass
import com.example.frontend.data.model.SendOtpBody
import com.example.frontend.data.model.ServerConfig
import com.example.frontend.data.model.Shop
import com.example.frontend.data.model.phoneNumberClass
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiClient {

//  ******************  Contacts APIs  ***********************
    @GET("contacts/search-contacts")
    suspend fun
            searchContacts(
        @Query("name") name:String?,
        @Query("startsWith") startsWithString:String?,
        @Query("village") village:String?,
        @Query("page") page:String?
    ): ApiResponse<List<Contact>>

    @GET("contacts/contact")
    suspend fun getContact(
        @Query("phoneNumber") name:String?,
    ): ApiResponse<Contact>
    @POST("contacts/contact")
    suspend fun addContact(
        @Body reqBody: ContactBody
    ): ApiResponse<Contact>

    @PUT("contacts/contact")
    suspend fun updateContact(
        @Body reqBody: ContactBody
    ): ApiResponse<Contact>

//  ******************  auth APIs  ***********************
    @POST("auth/send-otp")
    suspend fun sendOtp(
        @Body body: phoneNumberClass
        ): ApiResponse<String>

    @POST("auth/verify-otp")
    suspend fun verifyOTP(
        @Body reqBody: SendOtpBody
    ): ApiResponse<String>

    @POST("auth/login")
    suspend fun loginApi(
        @Body reqBody: ContactBody
    ): ApiResponse<Contact>

    @POST("auth/logout")
    suspend fun logoutApi():
            ApiResponse<String>

//  ******************  configs APIs  ***********************
    @GET("configs/getConfig")
    suspend fun getServerConfig()
    : ApiResponse<ServerConfig>

    @GET("configs/getVillages")
    suspend fun getVillages()
            : ApiResponse<List<String>>

//  ******************  shops APIs  ***********************
    @GET("shops/shop")
    suspend fun getShops(
        @Query("village") village:String?,
        @Query("page") page:String?,
    )
            : ApiResponse<List<Shop>>
//  ******************  shops APIs  ***********************
    @GET("events/events")
    suspend fun getEvent(
        @Query("village") village:String?,
        @Query("page") page:String?,
    )
            : ApiResponse<List<EventClass>>
}