package com.example.frontend.appComponent

import android.graphics.drawable.Drawable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import com.example.frontend.R
import com.example.frontend.utils.Screens

data class BottomNavItem(
    val name: String,
    val route: String,
    val icon: Int,
)

val bottomNavItems = listOf(
    BottomNavItem(
        name = "Shops",
        route = Screens.HomeScreen.route,
        icon =  R.drawable.icons_shop,
    ),
    BottomNavItem(
        name = "Contacts",
        route = Screens.ContactsScreen.route,
        icon = R.drawable.icon_contact
    ),
    BottomNavItem(
        name = "Events",
        route = Screens.AdsScreen.route,
        icon = R.drawable.icons_events,
    ),
)