package com.example.frontend.data.repoImpl

import com.example.frontend.data.model.ApiResponse
import com.example.frontend.data.repo.ContactsRepo
import com.example.frontend.data.repo.EventRepo
import com.example.frontend.data.retrofitInstance
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class EventRepoImpl: EventRepo {
    override suspend fun getEvent(village: String?, page: String): Flow<ApiResponse<*>> {
        return flow {
            val res = ApiCallHandler.handleApiCall {
                retrofitInstance.api.getEvent(
                    village,
                    page
                )
            }
            emit(res)
        }
    }

}