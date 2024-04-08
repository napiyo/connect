package com.example.frontend.api

import com.example.frontend.api.model.Contact
import com.example.frontend.api.model.Contacts
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiClient {

    @GET("/api/contacts/search-contacts/")
    suspend fun
            searchContacts(
        @Query("name") name:String?,
        @Query("startsWith") startsWithString:String?,
        @Query("village") village:String?
    ):ApiResponse<List<Contact>>

    @GET("contacts/contact/{phoneNumber}")
    suspend fun getContact(
        @Path("phoneNumber") phoneNumber:String
    ):ApiResponse<*>

}