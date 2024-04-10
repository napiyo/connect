package com.example.frontend.utils

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.frontend.screens.auth.LoginScreen
import com.example.frontend.screens.MainScreen
import com.example.frontend.screens.auth.OTPScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screens.MainScreen.route ){
        composable(route = Screens.MainScreen.route){
                MainScreen(navController = navController)
        }
        composable(route= Screens.LoginScreen.route){
            LoginScreen(navController = navController)
        }
        composable(route= Screens.OTPScreen.route+"/{phoneNumberProp}",
            arguments = listOf(navArgument("phoneNumberProp"){type = NavType.StringType})
        ){
            val phoneNumberProp = it.arguments?.getString("phoneNumberProp")?:""
            OTPScreen(navController = navController,phoneNumberProp)
        }
    }
}