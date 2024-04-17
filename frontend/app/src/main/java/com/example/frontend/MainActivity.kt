package com.example.frontend

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.datastore.preferences.preferencesDataStore
import com.example.frontend.data.model.ApiResponse
import com.example.frontend.data.repo.ContactsRepo
import com.example.frontend.data.repoImpl.ContactsRepoImpl
import com.example.frontend.utils.Navigation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    companion object {
        private lateinit var appContext: Context

        fun getAppContext(): Context {
            return appContext
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appContext = applicationContext
        setContent {
            Navigation()
        }
    }
}
