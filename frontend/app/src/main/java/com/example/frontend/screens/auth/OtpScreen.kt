package com.example.frontend.screens.auth

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.frontend.R
import com.example.frontend.appComponent.FloatingActionButtonAppComponent
import com.example.frontend.appComponent.HeadingAppComponent
import com.example.frontend.data.model.SendOtpBody
import com.example.frontend.data.repoImpl.AuthRepoImpl
import com.example.frontend.presentation.AuthViewModel
import com.example.frontend.ui.theme.PoppinsFont


@Composable
fun OTPScreen(navController: NavController,phoneNumberProp:String="")
{
// ******* models ********
    val authViewModel: AuthViewModel = remember { AuthViewModel(AuthRepoImpl()) }
    val verifyOtpState by authViewModel.verifyOtpState.collectAsState()
    val isLoading by authViewModel.isLoading.collectAsState()

// ******* locals ********
    var otpCode by remember {
        mutableStateOf("")
    }

    Scaffold(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxSize(),
        floatingActionButton = {
            FloatingActionButtonAppComponent(Icons.Filled.ArrowForward,"Next",isLoading){
                // next Action
                authViewModel.verifyOtp(SendOtpBody(phoneNumberProp,otpCode))
                if(!isLoading)
                {
                    Log.e("narendar",verifyOtpState.toString())
                }
            }
        },
        )

    {
        if(it.toString() == ""){}
        Column(modifier = Modifier
            .padding(top = 100.dp)
            .fillMaxSize()) {
            HeadingAppComponent(heading = "Please Enter OTP")
            Spacer(modifier = Modifier.height(20.dp))

            BasicTextField(value = otpCode , onValueChange = {otpCode = it.take(6)},
                modifier = Modifier.fillMaxSize(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
                ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    repeat(6){index ->
                        val number = when{
                            index < otpCode.length -> otpCode[index]
                            else -> ""
                        }

                        Text(
                            text = number.toString(),
                            fontSize = 30.sp,
                            fontFamily = PoppinsFont,
                            modifier = Modifier
                                .width(50.dp).height(50.dp)
                                .border(width = 1.dp,color= colorResource(id = R.color.OTPborder), shape = RoundedCornerShape(7.dp))
                                .padding(5.dp),
                            textAlign = TextAlign.Center
                        )
                    }

                   }
                }
            }

    }

}



@Preview
@Composable
fun OTPScreenPreview()
{
    OTPScreen(rememberNavController())
}