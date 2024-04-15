package com.example.frontend.presentation

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.toLowerCase
import androidx.lifecycle.ViewModel
import com.example.frontend.data.model.ApiResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.LinkedList
import java.util.Locale
import java.util.Queue

class NavViewModel:ViewModel() {
    private val _currentDestination = MutableStateFlow<String>("home_screen")
    val currentDestination: StateFlow<String> = _currentDestination
    private val _village = MutableStateFlow<String>("all")
    val village: StateFlow<String> = _village
    private val _villagelist = MutableStateFlow<List<String>>(listOf("all","amli","sanchore"))
    val villageList: StateFlow<List<String>> = _villagelist
    fun setCurrentDestination(destination: String) {
        _currentDestination.value = destination
    }
    fun setVillage(villageSelected:String)
    {
        _village.value = villageSelected.lowercase()
    }
    fun fetchVillages(){
//        _villagelist.value =
    }
    private val _snackbarQueue = MutableStateFlow<List<String>>(emptyList())
    val snackbarQueue: StateFlow<List<String>> = _snackbarQueue
    fun enqueueSnackbar(message: String) {
        _snackbarQueue.value+=(message)
    }
    fun dequeSnackBar(message:String)
    {
        _snackbarQueue.value -= message
    }

}