package com.example.frontend

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screens.LoginScreen .route ){
        composable(route = Screens.MainScreen.route){
                MainScreen(navController = navController)
        }
        composable(route=Screens.LoginScreen.route){
            LoginScreen(navController = navController)
        }
        composable(route=Screens.OTPScreen.route){
            OTPScreen(navController = navController)
        }

    }
}