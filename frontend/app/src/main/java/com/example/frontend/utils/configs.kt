package com.example.frontend.utils

import android.content.Context
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.frontend.MainActivity

object configs {
    val apiBaseUrl = "https://connect-xk71.onrender.com/api/"
    val SEARCH_RESULTS_PER_PAGE  = 100
    val MAX_RETRY  = 5
}
// Save token to SharedPreferences
fun saveToken(token: String) {
    val context = MainActivity.getAppContext()
    val sharedPreferences = context.getSharedPreferences("my_app_prefs", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString("token", token)
    editor.apply()
}

// Retrieve token from SharedPreferences
fun getToken(): String? {
    val context = MainActivity.getAppContext()
    val sharedPreferences = context.getSharedPreferences("my_app_prefs", Context.MODE_PRIVATE)
    return sharedPreferences.getString("token", null)
}
