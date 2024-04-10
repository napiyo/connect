package com.example.frontend.presentation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frontend.data.model.ApiResponse
import com.example.frontend.data.model.Contact
import com.example.frontend.data.model.ContactBody
import com.example.frontend.data.model.phoneNumberClass
import com.example.frontend.data.repo.ContactsRepo
import com.example.frontend.utils.configs
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ContactViewModel(private val contactsRepo: ContactsRepo): ViewModel() {
    private val _searchContactsState = MutableStateFlow<ApiResponse<*>>(ApiResponse(data = "initial"))
    val searchContactsState: StateFlow<ApiResponse<*>> = _searchContactsState

    private val _getContactState = MutableStateFlow<ApiResponse<*>>(ApiResponse(data = "initial"))
    val getContactState: StateFlow<ApiResponse<*>> = _getContactState

    private val _addContactState = MutableStateFlow<ApiResponse<*>>(ApiResponse(data = "initial"))
    val addContactState: StateFlow<ApiResponse<*>> = _addContactState

    private val _updateContactState = MutableStateFlow<ApiResponse<*>>(ApiResponse(data = "initial"))
    val updateContactState: StateFlow<ApiResponse<*>> = _updateContactState

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _contacts = MutableStateFlow<List<Contact>>(listOf(Contact(0,"",false,"","","",false,"","")))
    val contacts: StateFlow<List<Contact>> = _contacts

    private var currentPage: Int = 0

    // Functions to make API calls and update StateFlows
    fun searchContacts(name: String? = null, startsWith: String? = null, village: String? = null,page:String="0") {
        viewModelScope.launch {
            if(currentPage < 0) return@launch
            _isLoading.value = true
            contactsRepo.searchContacts(name, startsWith, village,currentPage.toString()).collect {

                if(it.success) {
                    val tempContacts: List<Contact> = it.data as List<Contact>
                    if(tempContacts.isNotEmpty())
                    {
                        _contacts.value += tempContacts
                    }
                    if(tempContacts.size == configs.SEARCH_RESULTS_PER_PAGE)
                    {
                        currentPage+=1;
                    }
                    else{
                        currentPage = -1
                    }
                }
                _searchContactsState.value = it
            }

            _isLoading.value = false
        }
    }

    fun getContact(body: phoneNumberClass) {
        viewModelScope.launch {
            _isLoading.value = true
            contactsRepo.getContact(body).collect {
                _getContactState.value = it
            }
            _isLoading.value = false
        }
    }

    fun addContact(reqBody: ContactBody) {
        viewModelScope.launch {
            _isLoading.value = true
            contactsRepo.addContact(reqBody).collect {
                _addContactState.value = it
            }
            _isLoading.value = false
        }
    }

    fun updateContact(reqBody: ContactBody) {
        viewModelScope.launch {
            _isLoading.value = true
            contactsRepo.updateContact(reqBody).collect {
                _updateContactState.value = it
            }
            _isLoading.value = false
        }
    }
    fun clearContacts()
    {
        _isLoading.value = true
        currentPage = 0;
        _contacts.value = listOf(Contact(0,"",false,"","","",false,"",""))
        _isLoading.value = false
    }
}