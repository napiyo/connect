package com.example.frontend.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.frontend.R
import com.example.frontend.ui.theme.PoppinsFont

@Composable
fun MaintenanceScreen(){
    Column(
        Modifier.fillMaxSize().background(Color.White).padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(painter = painterResource(id = R.drawable.maintenance_img),contentDescription = null ,
            Modifier.size(250.dp))
        Text(text = "Maintenace Mode", fontFamily = PoppinsFont, fontWeight = FontWeight.Medium, fontSize = 25.sp)
        Text(text = "We are updating our servers, please come back after some time", fontFamily = PoppinsFont, fontSize = 15.sp,
            textAlign = TextAlign.Center)

    }
}

@Preview
@Composable
fun MaintenanceScreenPreview()
{
    MaintenanceScreen()
}

@Composable
fun UpdateRequireScreen(){
    Column(
        Modifier.fillMaxSize().background(Color.White).padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(painter = painterResource(id = R.drawable.android),contentDescription = null ,
            Modifier.size(250.dp))
        Text(text = "Update Required", fontFamily = PoppinsFont, fontWeight = FontWeight.Medium, fontSize = 25.sp)
        Text(text = "Your current version of app is outdated.\n please update app from playStore", fontFamily = PoppinsFont, fontSize = 15.sp,
            textAlign = TextAlign.Center)

    }
}

@Preview
@Composable
fun UpdateRequireScreenPreview()
{
    UpdateRequireScreen()
}

@Composable
fun FailureScreen(){
    Column(
        Modifier.fillMaxSize().background(Color.White).padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(painter = painterResource(id = R.drawable.cloud_server),contentDescription = null ,
            Modifier.size(250.dp))
        Text(text = "Oops.. Servers are Unreachable", fontFamily = PoppinsFont, fontWeight = FontWeight.Medium, fontSize = 20.sp)
        Text(text = "Something went wrong with servers, please close this app and reopen", fontFamily = PoppinsFont, fontSize = 15.sp,
            textAlign = TextAlign.Center)

    }
}

@Preview
@Composable
fun FailureScreenPreview()
{
    FailureScreen()
}

@Composable
fun LoadingScreen(){
    Column(
        Modifier.fillMaxSize().background(Color.White).padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(painter = painterResource(id = R.drawable.logo),contentDescription = null ,
            Modifier.size(250.dp))
        Text(text = "Almost there...", fontFamily = PoppinsFont, fontWeight = FontWeight.Medium, fontSize = 20.sp)
        Text(text = "your Internet or Server are slow currently, \n Please wait...", fontFamily = PoppinsFont, fontSize = 15.sp,
            textAlign = TextAlign.Center)

    }
}

@Preview
@Composable
fun LoadingScreenpreview()
{
    LoadingScreen()
}