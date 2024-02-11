package com.example.frontend.data

import android.net.http.HttpException
import com.example.frontend.data.model.Contacts
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class ContactsRepoImpl (
    private val api:ApiClient
) : ContactsRepo {
    override suspend fun searchContacts(name:String? , startsWith:String?,village:String?): Flow<ApiResponse<Contacts>> {
        return flow {
            val contactsFromApi = try {
                api.searchContacts(name,startsWith,village)
            }catch (e:IOException){
                e.printStackTrace()
                emit(ApiResponse.Error("[Client Err] IOExecption - called searchContact"))
                return@flow
            }catch (e:Exception){
                e.printStackTrace()
                emit(ApiResponse.Error("[Client Err] Exception - called searchContact"))
                return@flow
            }
            emit(ApiResponse.Success(contactsFromApi))
        }
    }

}