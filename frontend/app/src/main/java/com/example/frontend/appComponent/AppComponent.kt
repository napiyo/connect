package com.example.frontend.appComponent



import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons

import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.frontend.R
import com.example.frontend.presentation.NavViewModel
import com.example.frontend.ui.theme.PoppinsFont


@Composable
fun FloatingActionButtonAppComponent(icon:ImageVector, iconDescription:String, isLoading : Boolean = false,onClickFunc: () -> Unit ){
    FloatingActionButton(onClick = onClickFunc,
        containerColor = colorResource(id = R.color.floatingButtonBackground),
        contentColor = colorResource(id = R.color.floatingButtonIcon),
        modifier = Modifier
            .statusBarsPadding()
            .navigationBarsPadding()
            .imePadding()
    ) {
        if(isLoading){
            CircularProgressIndicator()
        }else{
            Icon(icon, contentDescription = iconDescription)
        }
    }
}

@Preview
@Composable
fun FloatingActionButtonAppComponentPreview(){
    FloatingActionButtonAppComponent(Icons.Outlined.ArrowForward,
        iconDescription = "next",
    ){}
}

@Composable
fun HeadingAppComponent(heading:String){
    Text(text = heading, color = colorResource(id = R.color.textSimple),
        fontWeight = FontWeight.Medium, fontSize = 20.sp, textAlign = TextAlign.Center,
        fontFamily = PoppinsFont,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 5.dp))
}
@Preview
@Composable
fun HeadingAppComponentPreview(){
    HeadingAppComponent(heading = "Heading Example")
}


@Composable
fun OverlayLoadingScreen(modifier: Modifier = Modifier) {
    Surface(
        color = Color.Black.copy(alpha = 0.5f), // Translucent black color
        modifier = modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}

@Preview
@Composable
fun OverlayLoadingScreenPreview() {
    OverlayLoadingScreen()
}


@Composable
fun NavigationBarBottom(navViewModel: NavViewModel) {
    val currentDestination = navViewModel.currentDestination.collectAsState()
    NavigationBar(Modifier.height(75.dp)) {
        bottomNavItems.forEachIndexed { _,item ->
            val selected = item.route == currentDestination.value
            NavigationBarItem(
                icon = { Icon(painter = painterResource(id = item.icon), contentDescription = "",
                    tint = Color.Unspecified,
                    modifier = Modifier.size(20.dp)) },
                label = { Text(item.name, fontFamily = PoppinsFont) } ,
                selected = selected,
                onClick = { if(!selected) {navViewModel.setCurrentDestination(item.route)} }
            )
        }
    }
}
@Preview
@Composable
fun NavigationPreview() {
    NavigationBarBottom(navViewModel = NavViewModel())
}
@Composable
fun Header(iconId:Int,text:String){
    Row (horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(10.dp)){
        Icon(painter = painterResource(id = iconId), contentDescription = "", tint = Color.Unspecified,
            modifier = Modifier.size(30.dp))
        Spacer(modifier = Modifier.width(5.dp))
        Text(text=text,
            fontFamily = PoppinsFont, fontWeight = FontWeight.Medium, fontSize = 25.sp
        )
    }
    HorizontalDivider()
    Spacer(modifier = Modifier.height(5.dp))
}