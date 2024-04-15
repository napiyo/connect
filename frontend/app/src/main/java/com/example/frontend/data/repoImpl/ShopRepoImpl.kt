package com.example.frontend.data.repoImpl

import com.example.frontend.data.model.ApiResponse
import com.example.frontend.data.repo.ShopRepo
import com.example.frontend.data.retrofitInstance
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ShopRepoImpl:ShopRepo {
    override suspend fun getshops(village: String?, page: String): Flow<ApiResponse<*>> {
        return flow {
            val res = ApiCallHandler.handleApiCall {
                retrofitInstance.api.getShops(
                    village,
                    page
                )
            }
            emit(res)
        }
    }
}