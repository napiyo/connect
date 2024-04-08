package com.example.frontend.screens

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.frontend.appComponent.NavigationBarBottom

@Composable
fun Screen2() {
    Scaffold(
    ){
            if(it.toString() == "") {}
            Text(text = "Screen 2")
    }
}
@Preview
@Composable
fun Screen2Preview() {
    Screen2()
}