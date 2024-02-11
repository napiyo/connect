package com.example.frontend

sealed class Screens(val route: String) {
    object MainScreen : Screens("main_screen")
    object LoginScreen : Screens("login_screen")
    object OTPScreen : Screens("otp_screen")
}