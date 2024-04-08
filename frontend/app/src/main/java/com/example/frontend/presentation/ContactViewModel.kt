package com.example.frontend.presentation.MainScreenViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frontend.data.model.ApiResponse
import com.example.frontend.data.model.ContactBody
import com.example.frontend.data.repo.ContactsRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class contactViewModel(private val contactsRepo: ContactsRepo): ViewModel() {
    private val _searchContactsState = MutableStateFlow<ApiResponse<*>>(ApiResponse(data = "initial"))
    val searchContactsState: StateFlow<ApiResponse<*>> = _searchContactsState

    private val _getContactState = MutableStateFlow<ApiResponse<*>>(ApiResponse(data = "initial"))
    val getContactState: StateFlow<ApiResponse<*>> = _getContactState

    private val _addContactState = MutableStateFlow<ApiResponse<*>>(ApiResponse(data = "initial"))
    val addContactState: StateFlow<ApiResponse<*>> = _addContactState

    private val _updateContactState = MutableStateFlow<ApiResponse<*>>(ApiResponse(data = "initial"))
    val updateContactState: StateFlow<ApiResponse<*>> = _updateContactState

    // Functions to make API calls and update StateFlows
    fun searchContacts(name: String? = null, startsWith: String? = null, village: String? = null) {
        viewModelScope.launch {
            contactsRepo.searchContacts(name, startsWith, village).collect {
                _searchContactsState.value = it
            }
        }
    }

    fun getContact(phoneNumber: Int) {
        viewModelScope.launch {
            contactsRepo.getContact(phoneNumber).collect {
                _getContactState.value = it
            }
        }
    }

    fun addContact(reqBody: ContactBody) {
        viewModelScope.launch {
            contactsRepo.addContact(reqBody).collect {
                _addContactState.value = it
            }
        }
    }

    fun updateContact(reqBody: ContactBody) {
        viewModelScope.launch {
            contactsRepo.updateContact(reqBody).collect {
                _updateContactState.value = it
            }
        }
    }
}