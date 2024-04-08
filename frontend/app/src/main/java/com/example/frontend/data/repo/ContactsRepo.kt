package com.example.frontend.api.repo

import com.example.frontend.api.ApiResponse
import com.example.frontend.api.model.Contact
import com.example.frontend.api.model.Contacts
import kotlinx.coroutines.flow.Flow


interface ContactsRepo {
    suspend fun searchContacts(name:String?=null , startsWith:String?=null,village:String?=null):Flow<ApiResponse<*>>

}