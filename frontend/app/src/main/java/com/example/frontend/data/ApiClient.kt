package com.example.frontend.data

import com.example.frontend.data.model.Contact
import com.example.frontend.data.model.Contacts
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiClient {

    @GET("contacts/search-contacts/")
    suspend fun searchContacts(
        @Query("name") name:String?,
        @Query("startsWith") startsWithString:String?,
        @Query("village") village:String?
    ):Contacts

    @GET("contacts/contact/{phoneNumber}")
    suspend fun getContact(
        @Path("phoneNumber") phoneNumber:String
    ):Contact

    companion object {
        const val BASE_URL = "http://localhost:3000/api/"
    }
}