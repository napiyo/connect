package com.example.frontend.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.frontend.R
import com.example.frontend.utils.Screens
import com.example.frontend.appComponent.FloatingActionButtonAppComponent
import com.example.frontend.appComponent.HeadingAppComponent
import com.example.frontend.appComponent.OverlayLoadingScreen
import com.example.frontend.data.model.ContactBody
import com.example.frontend.data.model.phoneNumberClass
import com.example.frontend.data.repoImpl.AuthRepoImpl
import com.example.frontend.data.repoImpl.ContactsRepoImpl
import com.example.frontend.presentation.AuthViewModel
import com.example.frontend.presentation.ContactViewModel


@Composable
fun LoginScreen(navController: NavController) {
    val authViewModel: AuthViewModel = remember { AuthViewModel(AuthRepoImpl()) }
    val sendOtpState by authViewModel.sendOtpState.collectAsState()
    val contactViewModel: ContactViewModel = remember { ContactViewModel(ContactsRepoImpl()) }
    val addContactState by contactViewModel.addContactState.collectAsState()
    val isLoading by authViewModel.isLoading.collectAsState()
    var phoneNumberTF by remember {
        mutableStateOf("")
    }
    Scaffold(
        modifier = Modifier
            .padding(10.dp)
            .background(colorResource(id = R.color.appBackground)),
        floatingActionButton = {
            FloatingActionButtonAppComponent(Icons.Filled.ArrowForward,"Next"){

                authViewModel.sendOtp(phoneNumberClass( phoneNumberTF))
                if(sendOtpState.success)
                {
                    navController.navigate(Screens.OTPScreen.route)
                }
            }
        },


    )

    {paddingValues ->
        if(paddingValues.toString()==""){}
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Column( modifier = Modifier
                .height(300.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                HeadingAppComponent(heading = "Enter your Phone Number")
                OutlinedTextField(value = phoneNumberTF, onValueChange = {
                    phoneNumberTF = it.take(10) },
                    label = { Text(text = "Phone Number")},
                    singleLine = true,
                    keyboardOptions = KeyboardOptions( keyboardType = KeyboardType.Phone),
                    textStyle = TextStyle(fontSize = 20.sp),
                    modifier = Modifier
                        .width(350.dp)
                        .statusBarsPadding()
                        .navigationBarsPadding()
                        .imePadding(),
                    leadingIcon = { Icon(imageVector = Icons.Filled.Phone, contentDescription = "")},

                )
            }
        }
    }
}

@Preview(name="loginScreen", showBackground = true)
@Composable
fun LoginScreenPreview()
{
    LoginScreen(rememberNavController())
}