package com.example.frontend.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frontend.data.model.ApiResponse
import com.example.frontend.data.model.Contact
import com.example.frontend.data.model.EventClass
import com.example.frontend.data.model.Shop
import com.example.frontend.data.repo.ShopRepo
import com.example.frontend.utils.configs
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ShopsViewModel(private val shopRepo: ShopRepo) : ViewModel(){
    private val _shops = MutableStateFlow<List<Shop>>(listOf(Shop(0,"","","","","",0,"","","")))
    val shops: StateFlow<List<Shop>> = _shops
    private val _shopsState = MutableStateFlow<ApiResponse<*>>(ApiResponse(data = ""))
    val shopsState: StateFlow<ApiResponse<*>> = _shopsState
    private var currentPage: Int = 0
    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> = _isLoading
    private val _retryCount = MutableStateFlow<Int>(0)
    val retryCount: StateFlow<Int> = _retryCount
    fun getShops(village: String? = null) {
        if(currentPage < 0) return
        _isLoading.value = true
        viewModelScope.launch {
            var _village = if(village?.lowercase()=="all"){null}else{village}
            shopRepo.getshops(_village,currentPage.toString()).collect {

                if(it.success) {
                    val tempShops: List<Shop> = it.data as List<Shop>
                    if(tempShops.isNotEmpty())
                    {
                        _shops.value += tempShops
                    }
                    if(tempShops.size == configs.SEARCH_RESULTS_PER_PAGE)
                    {
                        currentPage+=1;
                    }
                    else{
                        currentPage = -1
                    }
                }
                else if (_retryCount.value == configs.MAX_RETRY){
                    currentPage = -1
                }
                else{
                    _retryCount.value+=1
                }
                _shopsState.value = it
            }

            _isLoading.value = false
        }
    }
    private var temp = 0;
    fun clearShops()
    {
        _isLoading.value = true
        currentPage = 0;
        _retryCount.value = 0
        _shops.value = listOf(Shop(temp,"","","","","",0,"","",""))
        _isLoading.value = false
        temp+=1;
    }
}