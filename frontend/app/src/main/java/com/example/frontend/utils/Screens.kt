package com.example.frontend.utils

sealed class Screens(val route: String) {
    object MainScreen : Screens("main_screen")
    object LoginScreen : Screens("login_screen")
    object OTPScreen : Screens("otp_screen")
    object HomeScreen : Screens("home_screen")
    object ContactsScreen : Screens("contacts_screen")
    object AdsScreen : Screens("ads_screen")
}