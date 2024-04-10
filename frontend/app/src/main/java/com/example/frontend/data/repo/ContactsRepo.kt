package com.example.frontend.data.repo

import com.example.frontend.data.model.ApiResponse
import com.example.frontend.data.model.ContactBody
import com.example.frontend.data.model.phoneNumberClass
import kotlinx.coroutines.flow.Flow


interface ContactsRepo {
    suspend fun searchContacts(name:String?=null , startsWith:String?=null,village:String?=null, page:String="0"):Flow<ApiResponse<*>>
    suspend fun getContact(body: phoneNumberClass):Flow<ApiResponse<*>>
    suspend fun addContact(reqBody : ContactBody):Flow<ApiResponse<*>>
    suspend fun updateContact(reqBody: ContactBody):Flow<ApiResponse<*>>

}