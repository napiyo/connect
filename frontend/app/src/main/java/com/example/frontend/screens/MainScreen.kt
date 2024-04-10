package com.example.frontend.screens


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.frontend.appComponent.NavigationBarBottom
import com.example.frontend.data.repoImpl.ContactsRepoImpl
import com.example.frontend.presentation.ContactViewModel
import com.example.frontend.presentation.NavViewModel
import com.example.frontend.utils.Screens

@Composable
fun MainScreen(navController: NavController){
    val navViewModel = remember { NavViewModel() }
    val currentDestination by navViewModel.currentDestination.collectAsState()
    val contactViewModel = remember { ContactViewModel(ContactsRepoImpl()) }
    val contactViewModelForQuery = remember { ContactViewModel(ContactsRepoImpl()) }

    var searchQuery by remember {
        mutableStateOf("")
    }
    Scaffold(
        bottomBar = { NavigationBarBottom(navViewModel) }
        ) {
        if(it.toString() == ""){}
        Box(modifier = Modifier.padding(bottom = 75.dp)) {
            when (currentDestination) {
                Screens.HomeScreen.route -> HomeScreen()
                Screens.ContactsScreen.route -> ContactsScreen(contactViewModel)
                Screens.AdsScreen.route -> AdsScreen()
            }
        }
    }
}


@Preview
@Composable
fun MainScreenPreview(){
    MainScreen(rememberNavController())
}

