package com.example.frontend.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.frontend.data.model.ApiResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class NavViewModel:ViewModel() {
    private val _currentDestination = MutableStateFlow<String>("home_screen")
    val currentDestination: StateFlow<String> = _currentDestination

    fun setCurrentDestination(destination: String) {
        _currentDestination.value = destination
    }
}