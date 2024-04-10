package com.example.frontend.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frontend.data.model.ApiResponse
import com.example.frontend.data.model.Contact
import com.example.frontend.data.model.ContactBody
import com.example.frontend.data.model.SendOtpBody
import com.example.frontend.data.model.phoneNumberClass
import com.example.frontend.data.repo.AuthRepo
import kotlinx.coroutines.delay
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
    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> = _isLoading
    // Functions to make API calls and update StateFlows
    fun sendOtp(body: phoneNumberClass) {
        viewModelScope.launch {
            _isLoading.value = true
            authRepo.sendOtp(body).collect {
                _sendOtpState.value = it
                Log.e("narendar","[here]"+it.toString())
            }
            _isLoading.value = false
        }
    }

    fun verifyOtp(reqBody: SendOtpBody) {
        viewModelScope.launch {
            _isLoading.value = true
            authRepo.verifyOTP(reqBody).collect {
                _verifyOtpState.value = it
            }
            _isLoading.value = false
        }
    }

    fun loginApi(reqBody: ContactBody) {
        viewModelScope.launch {
            _isLoading.value = true
            authRepo.loginApi(reqBody).collect {
                _loginApiState.value = it
            }
            _isLoading.value = false
        }
    }

    fun logoutApi() {
        viewModelScope.launch {
            _isLoading.value = true
            authRepo.logoutApi().collect {
                _logoutApiState.value = it
            }
            _isLoading.value = false
        }
    }
}