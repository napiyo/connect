package com.example.frontend.screens


import android.content.Context
import android.content.pm.PackageManager
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
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.frontend.MainActivity
import com.example.frontend.appComponent.HeaderWithDropDown
import com.example.frontend.appComponent.NavigationBarBottom
import com.example.frontend.data.model.ServerConfig
import com.example.frontend.data.repo.ServerConfigRepo
import com.example.frontend.data.repoImpl.ContactsRepoImpl
import com.example.frontend.data.repoImpl.EventRepoImpl
import com.example.frontend.data.repoImpl.ServerConfigRepoImpl
import com.example.frontend.data.repoImpl.ShopRepoImpl
import com.example.frontend.presentation.ContactViewModel
import com.example.frontend.presentation.EventViewModel
import com.example.frontend.presentation.NavViewModel
import com.example.frontend.presentation.ServerConfigViewModel
import com.example.frontend.presentation.ShopsViewModel
import com.example.frontend.screens.auth.LoginScreen
import com.example.frontend.ui.theme.snackBarColor
import com.example.frontend.utils.Screens
import com.example.frontend.utils.getToken
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch

@Composable
fun MainScreen(navController: NavController){

    // Local db

    val navViewModel = remember { NavViewModel() }
    val shopViewModel = remember { ShopsViewModel(ShopRepoImpl()) }
    val eventViewModel = remember { EventViewModel(EventRepoImpl()) }

    val currentDestination by navViewModel.currentDestination.collectAsState()
    val contactViewModel = remember { ContactViewModel(ContactsRepoImpl()) }
    val contactViewModelForQuery = remember { ContactViewModel(ContactsRepoImpl()) }
    val snackBarQueue by  navViewModel.snackbarQueue.collectAsState()

    val configViewModel = remember { ServerConfigViewModel(ServerConfigRepoImpl()) }
    val configState by configViewModel.configState.collectAsState()
    val villageListState by configViewModel.villageState.collectAsState()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit)
    {
        configViewModel.getConfig()
        configViewModel.getVillages()
    }
    LaunchedEffect(villageListState)
    {
       if(villageListState.success)
       {
           var _tempV = villageListState.data as List<String>
           if(_tempV.isNotEmpty())
           {
               _tempV = listOf("all") + _tempV
               navViewModel.setVillagesList(_tempV)
           }
       }
    }
    var screenToShow by remember {
        mutableStateOf(0)
    }
    fun getAppVer():Int
    {
        return try {
            val context = MainActivity.getAppContext()
            val pInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            pInfo.versionCode
        } catch (e: PackageManager.NameNotFoundException) {
            Log.e("narendra",e.message.toString())
            1
        }
    }
    var firstTimeLaunched by remember {
        mutableStateOf(true)
    }
    LaunchedEffect(configState)
    {
        if(firstTimeLaunched)
        {
            firstTimeLaunched = false
            return@LaunchedEffect
        }

        if (configState.success){
            val tempRes = configState.data as ServerConfig
            if(tempRes.requiredVer > getAppVer())
            {
                screenToShow = 2
            }
            else if(tempRes.mode.lowercase() == "maintenance")
            {
                screenToShow = 3
            }
            else if(getToken() == ""){
                screenToShow=4
                navController.navigate(Screens.LoginScreen.route, builder = {
                    popUpTo(Screens.LoginScreen.route) { inclusive = true } // Set inclusive to true to also remove the destination itself
                })
            }
            else{
                screenToShow =5
            }
        }
        else{
            screenToShow = 1

        }
    }
    LaunchedEffect(snackBarQueue){
        if(snackBarQueue.isNotEmpty()){
        scope.launch {
                val msg = snackBarQueue.last()
                navViewModel.dequeSnackBar(msg)
                snackbarHostState.showSnackbar(msg, duration = SnackbarDuration.Short)
            }
        }
    }
    /*
 Screens
 0-> loading
 1-> failure screen
 2-> update Android app
 3->maintenance mode
 4->login screen
 5->main screen
     */

    when(screenToShow){
        0-> LoadingScreen()
        1-> FailureScreen()
        2-> UpdateRequireScreen()
        3-> MaintenanceScreen()
        4-> LoginScreen(navController = navController)
        5->{
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

    }

}


@Preview
@Composable
fun MainScreenPreview(){
    MainScreen(rememberNavController())
}

