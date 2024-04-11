package com.example.frontend.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.frontend.R
import com.example.frontend.appComponent.Header
import com.example.frontend.appComponent.HeadingAppComponent
import com.example.frontend.ui.theme.PoppinsFont
import com.example.frontend.ui.theme.SatisfyFont
import com.example.frontend.ui.theme.eventDateColor
import com.example.frontend.ui.theme.ribbenIconBGColor
import com.example.frontend.ui.theme.weedingContainerColor

@Composable
fun AdsScreen() {
    Scaffold ()
    {
        if(it.toString() == "") {}
        Column {
            Header(iconId = R.drawable.icons_events, text ="Events" )
            
        }
    }
}
//@Preview
//@Composable
//fun AdsScreenPreview() {
//    AdsScreen()
//}
@Composable
fun SocialIcon()
{
    Icon(painter = painterResource(R.drawable.icon_contact), contentDescription ="",tint= Color.Unspecified,
        modifier = Modifier.size(25.dp).padding(horizontal = 2.dp))
}
@Composable
fun BirthdayTextItem()
{
    HeadingAppComponent(heading = "Happy Birthday")
    HorizontalDivider(color = Color.Black, modifier = Modifier.offset(15.dp))
    Spacer(modifier = Modifier.height(5.dp))
    HeadingAppComponent(heading = "Narendar Dewasi")
    Row(horizontalArrangement = Arrangement.Center, verticalAlignment =Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
            .padding(bottom = 5.dp)) {
    SocialIcon()
    SocialIcon()
    SocialIcon()
    }
}
@Composable
fun WeddingEventItem()
{
    Column {

        Row(
            Modifier
                .height(125.dp)
                .padding(end = 12.dp), verticalAlignment = Alignment.Bottom
        ) {
            Box(
                Modifier
                    .fillMaxHeight()
                    .zIndex(5f)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.couple),
                    tint = Color.Unspecified,
                    contentDescription = "",
                    modifier = Modifier
                        .size(40.dp)
                        .background(ribbenIconBGColor, shape = RoundedCornerShape(25.dp))
                        .padding(5.dp)
                )
            }
            Row(
                Modifier
                    .height(100.dp)
                    .offset(y = -13.dp)
                    .background(color = Color.Unspecified, shape = RoundedCornerShape(5.dp))
                    .fillMaxWidth(1f),

                ) {
                Row(
                    Modifier

                        .background(weedingContainerColor, shape = RoundedCornerShape(5.dp))
                        .offset(x = -25.dp)
                ) {
                    Row {
                        Column(
                            Modifier
                                .width(70.dp)
                                .fillMaxHeight()
                                .background(eventDateColor, shape = RoundedCornerShape(5.dp))
                                .padding(5.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        )
                        {
                            Text(
                                text = "20",
                                textAlign = TextAlign.Center,
                                fontFamily = PoppinsFont,
                                fontWeight = FontWeight.Thin,
                                fontSize = 30.sp,
                            )
                            Text("march", fontFamily = PoppinsFont)
                        }
                        VerticalDivider()
                    }
                    Column(
                        Modifier
                            .fillMaxHeight()
                            .fillMaxWidth()
                    ) {
//                        weddingTextContainer()
                        BirthdayTextItem()
                    }
                }
            }
        }
        Row(
            Modifier
                .fillMaxWidth()
                .height(40.dp)
                .padding(horizontal = 25.dp)
                .offset(y = -3.dp)
                .background(Color.White, RoundedCornerShape(5.dp))
                .padding(5.dp),
            verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Narendra Dewasi", fontFamily = PoppinsFont)
                Box{
                    Row {


                        Icon(
                            painter = painterResource(id = R.drawable.icon_call),
                            contentDescription = "",
                            tint = Color.Unspecified,
                            modifier = Modifier
                                .clickable { }
                                .padding(end = 5.dp)
                        )

                        Icon(
                            painter = painterResource(id = R.drawable.icons_download),
                            contentDescription = "",
                            tint = Color.Unspecified,
                            modifier = Modifier
                                .clickable { }
                                .padding(start = 5.dp)
                                .size(35.dp)
                        )
                    }
                }

        }
        Spacer(modifier = Modifier.height(3.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(3.dp))
    }
}
@Composable
fun HeadingWithoutPadding(heading:String,modifier: Modifier)
{
    Text(text = heading, color = colorResource(id = R.color.textSimple),
        fontWeight = FontWeight.Medium, fontSize = 20.sp, textAlign = TextAlign.Center,
        fontFamily = PoppinsFont,
        modifier = modifier
            .fillMaxWidth())
}
@Preview
@Composable
fun EventItemPreview()
{
    WeddingEventItem()
}
@Composable
fun weddingTextContainer()
{
    HeadingWithoutPadding(
        heading = "Narendra Kumar Dewasi",
        Modifier.padding(top = 5.dp)
    )
    Text(
        "weds", fontFamily = SatisfyFont, modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp), fontSize = 22.sp, textAlign = TextAlign.Center
    )
    HeadingWithoutPadding(
        heading = "Narendra Kumar Dewasi",
        Modifier.padding(bottom = 5.dp)
    )
}