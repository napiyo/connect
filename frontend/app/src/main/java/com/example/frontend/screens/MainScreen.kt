package com.example.frontend.screens


import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.frontend.appComponent.HeaderWithDropDown
import com.example.frontend.appComponent.NavigationBarBottom
import com.example.frontend.data.repoImpl.ContactsRepoImpl
import com.example.frontend.data.repoImpl.EventRepoImpl
import com.example.frontend.data.repoImpl.ShopRepoImpl
import com.example.frontend.presentation.ContactViewModel
import com.example.frontend.presentation.EventViewModel
import com.example.frontend.presentation.NavViewModel
import com.example.frontend.presentation.ShopsViewModel
import com.example.frontend.ui.theme.snackBarColor
import com.example.frontend.utils.Screens
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch

@Composable
fun MainScreen(navController: NavController){
    val navViewModel = remember { NavViewModel() }
    val shopViewModel = remember { ShopsViewModel(ShopRepoImpl()) }
    val eventViewModel = remember { EventViewModel(EventRepoImpl()) }

    val currentDestination by navViewModel.currentDestination.collectAsState()
    val contactViewModel = remember { ContactViewModel(ContactsRepoImpl()) }
    val contactViewModelForQuery = remember { ContactViewModel(ContactsRepoImpl()) }
    val snackBarQueue by  navViewModel.snackbarQueue.collectAsState()


    var searchQuery by remember {
        mutableStateOf("")
    }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(snackBarQueue){
        if(snackBarQueue.isNotEmpty()){
        scope.launch {
                val msg = snackBarQueue.last()
                navViewModel.dequeSnackBar(msg)
                snackbarHostState.showSnackbar(msg, duration = SnackbarDuration.Short)
            }
        }
    }
    Scaffold(
        bottomBar = { NavigationBarBottom(navViewModel) },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState){Snackbar(
                snackbarData = it,
                containerColor = snackBarColor,
            )}
        },
        ) {
        if(it.toString() == ""){}
            Box(modifier = Modifier.padding(bottom = 75.dp)) {
                when (currentDestination) {
                    Screens.HomeScreen.route -> HomeScreen(navViewModel,shopViewModel)
                    Screens.ContactsScreen.route -> ContactsScreen(contactViewModel,contactViewModelForQuery,navViewModel)
                    Screens.AdsScreen.route -> AdsScreen(navViewModel,eventViewModel)
                }
            }
    }
}


@Preview
@Composable
fun MainScreenPreview(){
    MainScreen(rememberNavController())
}

