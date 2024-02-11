package com.example.frontend.data

import com.example.frontend.data.model.Contacts
import kotlinx.coroutines.flow.Flow


interface ContactsRepo {
    suspend fun searchContacts(name:String? , startsWith:String?,village:String?):Flow<ApiResponse<Contacts>>

}