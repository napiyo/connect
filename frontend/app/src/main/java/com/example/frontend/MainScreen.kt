package com.example.frontend

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.navigation.NavController

@Composable
fun MainScreen(navController: NavController){
    Box(
        contentAlignment = Alignment.Center
    )
    {
        Text(text = "Main Screen")
    }
}