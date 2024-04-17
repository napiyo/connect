package com.example.frontend.screens.auth

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.frontend.R
import com.example.frontend.appComponent.FloatingActionButtonAppComponent
import com.example.frontend.appComponent.HeadingAppComponent
import com.example.frontend.data.model.SendOtpBody
import com.example.frontend.data.model.phoneNumberClass
import com.example.frontend.data.repoImpl.AuthRepoImpl
import com.example.frontend.presentation.AuthViewModel
import com.example.frontend.ui.theme.PoppinsFont
import com.example.frontend.utils.Screens
import kotlinx.coroutines.delay


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
    var errMsg by remember {
        mutableStateOf("")
    }

    LaunchedEffect(verifyOtpState)
    {
        if(verifyOtpState.success)
        {
            navController.navigate(Screens.ProfileLoginScreen.route + "/${verifyOtpState.data}"+"/${phoneNumberProp}",
                builder = {
                    popUpTo(Screens.LoginScreen.route) { inclusive = true } // Set inclusive to true to also remove the destination itself
                })
        }
        else{
            errMsg=verifyOtpState.data.toString()
        }
    }
    Scaffold(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxSize(),
        floatingActionButton = {
            FloatingActionButtonAppComponent(Icons.Filled.ArrowForward,"Next",isLoading){
                // next Action
                authViewModel.verifyOtp(SendOtpBody(phoneNumberProp,otpCode))
//                if(!isLoading)
//                {
//                    Log.e("narendar",verifyOtpState.toString())
//                }
            }
        },
        )

    {
        if(it.toString() == ""){}
        Column(modifier = Modifier
            .padding(top = 100.dp)
            .fillMaxSize()) {
            HeadingAppComponent(heading = "Please Enter OTP")
//            Spacer(modifier = Modifier.height(.dp))
            Row(Modifier.fillMaxWidth().padding(10.dp), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                Text(
                    "OTP sent on ${phoneNumberProp} ", fontFamily = PoppinsFont,

//                        .fillMaxWidth()
//                        .padding(10.dp)
                )
                Text("wrong number ?",fontFamily = PoppinsFont,
                    textDecoration = TextDecoration.Underline, fontWeight = FontWeight.Medium,
                    modifier = Modifier.clickable {
                        navController.navigate(Screens.LoginScreen.route, builder = {
                            popUpTo(Screens.LoginScreen.route) { inclusive = false } // Set inclusive to true to also remove the destination itself
                        })
                    })
            }
            BasicTextField(value = otpCode , onValueChange = {
                otpCode = it.take(6)
                errMsg=""
                                                             },
                modifier = Modifier.fillMaxWidth(),
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
                                .width(50.dp)
                                .height(50.dp)
                                .border(
                                    width = 1.dp,
                                    color = colorResource(id = R.color.OTPborder),
                                    shape = RoundedCornerShape(7.dp)
                                )
                                .padding(5.dp),
                            textAlign = TextAlign.Center
                        )
                    }

                   }
                }
            Spacer(modifier = Modifier.height(10.dp))
            OtpTimer(phoneNumberProp,authViewModel)
            Text(text = errMsg, fontSize = 12.sp, fontFamily = PoppinsFont, color = Color.Red,textAlign=TextAlign.Center,
                modifier = Modifier.fillMaxWidth())
        }

    }

}

@Composable
fun OtpTimer(phoneNumberProp: String,authViewModel: AuthViewModel) {
    var timer by remember { mutableStateOf(60) }
    var isTimerRunning by remember { mutableStateOf(true) }
    var temp by remember { mutableStateOf(1) }

    LaunchedEffect(temp) {
        if(temp >5)
        {
            return@LaunchedEffect
        }
        isTimerRunning = true
        while (timer > 0 && isTimerRunning) {
            delay(1000)
            timer--
        }
        isTimerRunning = false
        timer = 60
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        if (!isTimerRunning) {
            if(temp > 4){
                Text("Reached max retry, blocked for 24 hr",fontFamily = PoppinsFont, fontWeight = FontWeight.Medium,
                    color = Color.Red
                    )
            }
            else{

            Text("Send otp again ?",textDecoration = TextDecoration.Underline, fontFamily = PoppinsFont, fontWeight = FontWeight.Medium,
                modifier = Modifier.clickable {
                    authViewModel.sendOtp(phoneNumberClass(phoneNumberProp))
                    temp++
            } )
            }
        }else{
            Text(text = "wait before sending new otp : $timer sec",fontFamily = PoppinsFont)
        }


    }
}

@Preview
@Composable
fun OTPScreenPreview()
{
    OTPScreen(rememberNavController(),"7976224044")
}
//@Preview
//@Composable
//fun PreviewOtpTimerScreen() {
//    OtpTimer("", AuthViewModel(AuthRepoImpl()))
//}