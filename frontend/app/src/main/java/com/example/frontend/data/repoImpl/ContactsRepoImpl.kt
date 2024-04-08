package com.example.frontend.data.repo

import com.example.frontend.data.model.ApiResponse
import com.example.frontend.data.model.ContactBody
import com.example.frontend.data.retrofitInstance
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ContactsRepoImpl () : ContactsRepo {
    override suspend fun searchContacts(name:String? , startsWith:String?,village:String?): Flow<ApiResponse<*>> {
        return flow {
            val res = ApiCallHandler.handleApiCall {retrofitInstance.api.searchContacts(name, startsWith, village)  }
            emit(res)
        }
    }

    override suspend fun getContact(phoneNumber: Int): Flow<ApiResponse<*>> {
        return flow {
            val res = ApiCallHandler.handleApiCall {retrofitInstance.api.getContact(phoneNumber)  }
            emit(res)
        }
    }

    override suspend fun addContact(reqBody: ContactBody): Flow<ApiResponse<*>> {
        return flow {
            val res = ApiCallHandler.handleApiCall {retrofitInstance.api.addContact(reqBody)  }
            emit(res)
        }
    }

    override suspend fun updateContact(reqBody: ContactBody): Flow<ApiResponse<*>> {
        return flow {
            val res = ApiCallHandler.handleApiCall {retrofitInstance.api.updateContact(reqBody)  }
            emit(res)
        }
    }

}