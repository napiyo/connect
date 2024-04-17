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
import com.example.frontend.utils.configs.MAX_RETRY
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ContactViewModel(private val contactsRepo: ContactsRepo): ViewModel() {
    private val _searchContactsState = MutableStateFlow<ApiResponse<*>>(ApiResponse(data = ""))
    val searchContactsState: StateFlow<ApiResponse<*>> = _searchContactsState

    private val _getContactState = MutableStateFlow<ApiResponse<*>>(ApiResponse(data = ""))
    val getContactState: StateFlow<ApiResponse<*>> = _getContactState

    private val _addContactState = MutableStateFlow<ApiResponse<*>>(ApiResponse(data = ""))
    val addContactState: StateFlow<ApiResponse<*>> = _addContactState

    private val _updateContactState = MutableStateFlow<ApiResponse<*>>(ApiResponse(data = ""))
    val updateContactState: StateFlow<ApiResponse<*>> = _updateContactState

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _contacts = MutableStateFlow<List<Contact>>(listOf(Contact(0,"",false,"","","",false,"","")))
    val contacts: StateFlow<List<Contact>> = _contacts

    private var currentPage: Int = 0

    private val _retryCount = MutableStateFlow<Int>(0)
    val retryCount: StateFlow<Int> = _retryCount

    private val _prevVillage = MutableStateFlow<String>("all")
    val prevVillage: StateFlow<String> = _prevVillage

    // Functions to make API calls and update StateFlows
    fun searchContacts(name: String? = null, startsWith: String? = null, village: String) {
        if(currentPage < 0) return
        _isLoading.value = true
        viewModelScope.launch {
            var _village = if(village?.lowercase()=="all"){null}else{village}
            contactsRepo.searchContacts(name, startsWith, _village,currentPage.toString()).collect {

                if(it.success) {
                    val tempContacts: List<Contact> = it.data as List<Contact>
                    if(tempContacts.isNotEmpty())
                    {
                        _contacts.value = (_contacts.value + tempContacts).toSet().toList()
                    }
                    if(tempContacts.size == configs.SEARCH_RESULTS_PER_PAGE)
                    {
                        currentPage+=1;
                    }
                    else{
                        currentPage = -1
                    }
                }
                else if (_retryCount.value == MAX_RETRY){
                    currentPage = -1
                }
                else{
                    _retryCount.value+=1
                }
                _searchContactsState.value = it
            }
            _prevVillage.value = village
            _isLoading.value = false
        }
    }

    fun getContact(phoneNumber:String) {
        viewModelScope.launch {
            _isLoading.value = true
            contactsRepo.getContact(phoneNumber).collect {
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
    private var temp = 0;
    fun clearContacts()
    {
        _isLoading.value = true
        currentPage = 0;
        _retryCount.value = 0
        _contacts.value = listOf(Contact(temp,"",false,"","","",false,"",""))
        _isLoading.value = false
        temp+=1;
    }
}