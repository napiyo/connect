package com.example.frontend.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frontend.data.model.ApiResponse
import com.example.frontend.data.model.Contact
import com.example.frontend.data.model.EventClass
import com.example.frontend.data.model.Shop
import com.example.frontend.data.repo.EventRepo
import com.example.frontend.data.repo.ShopRepo
import com.example.frontend.utils.configs
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class EventViewModel(private val eventRepo: EventRepo) : ViewModel() {
    private val _events =
        MutableStateFlow<List<EventClass>>(listOf(EventClass(0, "", "", 1, "", "", "", "", "", priority = 0, type = "","","")))
    val events: StateFlow<List<EventClass>> = _events
    private val _eventsState = MutableStateFlow<ApiResponse<*>>(ApiResponse(data = ""))
    val eventsState: StateFlow<ApiResponse<*>> = _eventsState
    private var currentPage: Int = 0
    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> = _isLoading
    private val _retryCount = MutableStateFlow<Int>(0)
    val retryCount: StateFlow<Int> = _retryCount

    fun getEvents(village: String? = null) {
        if (currentPage < 0) return
        _isLoading.value = true
        viewModelScope.launch {
            var _village = if (village?.lowercase() == "all") {
                null
            } else {
                village
            }
            eventRepo.getEvent(_village, currentPage.toString()).collect {

                if (it.success) {
                    val tempShops: List<EventClass> = it.data as List<EventClass>
                    if (tempShops.isNotEmpty()) {
                        _events.value += tempShops
                    }
                    if (tempShops.size == configs.SEARCH_RESULTS_PER_PAGE) {
                        currentPage += 1;
                    } else {
                        currentPage = -1
                    }
                } else if (_retryCount.value == configs.MAX_RETRY) {
                    currentPage = -1
                } else {
                    _retryCount.value += 1
                }
                _eventsState.value = it
            }

            _isLoading.value = false
        }
    }
    private var temp = 0;
    fun clearEvents()
    {
        _isLoading.value = true
        currentPage = 0;
        _retryCount.value = 0
        _events.value = listOf(EventClass(temp, "", "", 1, "", "", "", "", "", priority = 0, type = "","",""))
        _isLoading.value = false
        temp+=1;
    }
}
