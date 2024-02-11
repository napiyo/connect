package com.example.frontend.appComponent



import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons

import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.frontend.R
import java.nio.file.WatchEvent


@Composable
fun FloatingActionButtonAppComponent(icon:ImageVector, iconDescription:String,onClickFunc: () -> Unit ){
    FloatingActionButton(onClick = onClickFunc,
        containerColor = colorResource(id = R.color.floatingButtonBackground),
        contentColor = colorResource(id = R.color.floatingButtonIcon),
        modifier = Modifier
            .statusBarsPadding()
            .navigationBarsPadding()
            .imePadding()
    ) {
        
        Icon(icon, contentDescription = iconDescription)
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
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 5.dp))
}
@Preview
@Composable
fun HeadingAppComponentPreview(){
    HeadingAppComponent(heading = "Heading Example")
}