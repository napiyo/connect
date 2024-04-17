package com.example.frontend.utils

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.frontend.screens.auth.LoginScreen
import com.example.frontend.screens.MainScreen
import com.example.frontend.screens.auth.OTPScreen
import com.example.frontend.screens.auth.ProfileLoginScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screens.MainScreen.route ){

        composable(route = Screens.MainScreen.route){
            BackHandler(true) {
                // Or do nothing
            }
                MainScreen(navController = navController)
        }
        composable(route= Screens.LoginScreen.route){
            BackHandler(true) {
                // Or do nothing
            }
            LoginScreen(navController = navController)
        }
        composable(route= Screens.OTPScreen.route+"/{phoneNumberProp}",
            arguments = listOf(navArgument("phoneNumberProp"){type = NavType.StringType})
        ){
            BackHandler(true) {
                // Or do nothing
            }
            val phoneNumberProp = it.arguments?.getString("phoneNumberProp")?:""
            OTPScreen(navController = navController,phoneNumberProp)
        }
        composable(route= Screens.ProfileLoginScreen.route+"/{token}/{phoneNumber}",
            arguments = listOf(navArgument("token"){type = NavType.StringType},
                navArgument("phoneNumber") { type = NavType.StringType })
        ){
            BackHandler(true) {
                // Or do nothing
            }
            val token = it.arguments?.getString("token")?:""
            val phoneNumber = it.arguments?.getString("phoneNumber")?:""
            ProfileLoginScreen(navController = navController,token,phoneNumber)
        }
    }
}