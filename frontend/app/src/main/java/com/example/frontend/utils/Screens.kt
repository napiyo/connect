package com.example.frontend.utils

import com.example.frontend.R

sealed class Screens(val route: String) {
    data object MainScreen : Screens("main_screen")
    data object LoginScreen : Screens("login_screen")
    data object OTPScreen : Screens("otp_screen")
    data object HomeScreen : Screens("home_screen"){
        val text = "Shops"
        val icon = R.drawable.icons_shop
    }
    data object ContactsScreen : Screens("contacts_screen")
    {
        val text = "Contacts"
        val icon = R.drawable.icon_contact
    }
    data object AdsScreen : Screens("ads_screen")
    {
        val text = "Events"
        val icon = R.drawable.icons_events
    }
}