package com.example.frontend.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frontend.data.model.ApiResponse
import com.example.frontend.data.model.Contact
import com.example.frontend.data.repo.ServerConfigRepo
import com.example.frontend.utils.configs
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ServerConfigViewModel(private val
                            serverConfig:ServerConfigRepo):ViewModel() {

    private val _configState = MutableStateFlow<ApiResponse<*>>(ApiResponse(data = ""))
    val configState: StateFlow<ApiResponse<*>> = _configState

    private val _villageState = MutableStateFlow<ApiResponse<*>>(ApiResponse(data = ""))
    val villageState: StateFlow<ApiResponse<*>> = _villageState

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun getConfig() {
        _isLoading.value = true
        viewModelScope.launch {
            serverConfig.getServerConfig().collect {
                _configState.value = it
            }
            _isLoading.value = false
        }
    }
    fun getVillages() {
        _isLoading.value = true
        viewModelScope.launch {
            serverConfig.getVillages().collect {
                _villageState.value = it
            }
            _isLoading.value = false
        }
    }
}