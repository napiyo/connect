package com.example.frontend.presentation.MainScreenViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frontend.data.model.ApiResponse
import com.example.frontend.data.model.Contact
import com.example.frontend.data.model.ContactBody
import com.example.frontend.data.model.SendOtpBody
import com.example.frontend.data.repo.AuthRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val authRepo: AuthRepo) : ViewModel() {

    // Define StateFlows for each API call
    private val _sendOtpState = MutableStateFlow<ApiResponse<String>>(ApiResponse())
    val sendOtpState: StateFlow<ApiResponse<String>> = _sendOtpState

    private val _verifyOtpState = MutableStateFlow<ApiResponse<String>>(ApiResponse())
    val verifyOtpState: StateFlow<ApiResponse<String>> = _verifyOtpState

    private val _loginApiState = MutableStateFlow<ApiResponse<*>>(ApiResponse<Contact>())
    val loginApiState: StateFlow<ApiResponse<*>> = _loginApiState

    private val _logoutApiState = MutableStateFlow<ApiResponse<String>>(ApiResponse())
    val logoutApiState: StateFlow<ApiResponse<String>> = _logoutApiState

    // Functions to make API calls and update StateFlows
    fun sendOtp(phoneNumber: Int) {
        viewModelScope.launch {
            authRepo.sendOtp(phoneNumber).collect {
                _sendOtpState.value = it
            }
        }
    }

    fun verifyOtp(reqBody: SendOtpBody) {
        viewModelScope.launch {
            authRepo.verifyOTP(reqBody).collect {
                _verifyOtpState.value = it
            }
        }
    }

    fun loginApi(reqBody: ContactBody) {
        viewModelScope.launch {
            authRepo.loginApi(reqBody).collect {
                _loginApiState.value = it
            }
        }
    }

    fun logoutApi() {
        viewModelScope.launch {
            authRepo.logoutApi().collect {
                _logoutApiState.value = it
            }
        }
    }
}