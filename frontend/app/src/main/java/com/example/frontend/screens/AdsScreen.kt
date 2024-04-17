package com.example.frontend.screens

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.frontend.R
import com.example.frontend.appComponent.HeaderWithDropDown
import com.example.frontend.appComponent.HeadingAppComponent
import com.example.frontend.appComponent.SocialIcon
import com.example.frontend.data.model.EventClass
import com.example.frontend.data.repoImpl.EventRepoImpl
import com.example.frontend.presentation.EventViewModel
import com.example.frontend.presentation.NavViewModel
import com.example.frontend.ui.theme.PoppinsFont
import com.example.frontend.ui.theme.SatisfyFont
import com.example.frontend.ui.theme.bdayContainerColor
import com.example.frontend.ui.theme.eventDateColor
import com.example.frontend.ui.theme.generalContainerColor
import com.example.frontend.ui.theme.ribbenIconBGColor
import com.example.frontend.ui.theme.shopContainerColor
import com.example.frontend.ui.theme.weedingContainerColor
import com.example.frontend.utils.configs
import com.example.frontend.utils.getToken
import com.example.frontend.utils.saveToken

@Composable
fun AdsScreen(navViewModal:NavViewModel, eventViewModel: EventViewModel) {
    val eventState by eventViewModel.eventsState.collectAsState()
    val events by eventViewModel.events.collectAsState()
    val isLoading by eventViewModel.isLoading.collectAsState()
    val retryCount by eventViewModel.retryCount.collectAsState()
    val villageSelected by navViewModal.village.collectAsState()
    val prevVillage by eventViewModel.prevVillage.collectAsState()
    LaunchedEffect(eventState)
    {
        if(!eventState.success && eventState.data.toString().isNotBlank())
        {
            navViewModal.enqueueSnackbar(eventState.data.toString())
        }
    }
    LaunchedEffect(retryCount)
    {
        if(retryCount== configs.MAX_RETRY)
        {
            navViewModal.enqueueSnackbar("we couldn't fetch events, please retry")
        }
    }
    LaunchedEffect(villageSelected)
    {

        if(prevVillage.lowercase() != villageSelected.lowercase()){
            eventViewModel.clearEvents()
        }
    }
    val context = LocalContext.current

    Scaffold ()
    {
        if(it.toString() == "") {}
        Column {
                HeaderWithDropDown(iconId = R.drawable.icons_events, text ="Events", navViewModal  )
                HorizontalDivider()
            LazyColumn{
                itemsIndexed(events){index,item ->
                    if(isLoading)
                    {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            for (i in 1..6)
                            {
                                ShimmerAdsItem()
                            }
                        }
                    }
                    EventItem(item,navViewModal)
                    if( !isLoading &&  events.lastIndex == index){
                    if(retryCount >= configs.MAX_RETRY)
                    {
                        Text(text = "failed to get more events", maxLines = 1, modifier = Modifier.padding(5.dp))
                    }
                    else{
                        eventViewModel.getEvents(village = villageSelected)
                    }
                    if(events.size==1){
                        Text("No Events found", modifier = Modifier.padding(5.dp))
                    }
                }
            }
            }

        }
    }
}
@Preview
@Composable
fun AdsScreenPreview() {
    AdsScreen(NavViewModel(), EventViewModel(EventRepoImpl()))
}

@Composable
fun BirthdayTextItem(item:EventClass,navViewModal:NavViewModel)
{
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { }
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {

        Column (
            Modifier
                .offset(x = 8.dp)
                .weight(1f)){
            Text(text= "Happy Birthday", fontSize = 12.sp,fontFamily = PoppinsFont, fontWeight = FontWeight.Normal, textAlign = TextAlign.Center, modifier = Modifier
                .padding(bottom = 5.dp)
                .fillMaxWidth())
//            HorizontalDivider(color = Color.Black, modifier = Modifier.offset(0.dp))
            Spacer(modifier = Modifier.height(5.dp))
            HeadingAppComponent(heading = item.heading)
        }
        VerticalDivider(Modifier.offset(x = 15.dp))
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .width(40.dp)
                .offset(x = 15.dp)
                .padding(bottom = 5.dp)) {
            SocialIcon(R.drawable.icon_call){
                try {
                    val uri = Uri.parse("https://api.whatsapp.com/send?phone=${item.phoneNumber}")
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    launcher.launch(intent)
                }
                catch (e:Exception){
                    navViewModal.enqueueSnackbar(e.message.toString())
                }
            }
            SocialIcon(R.drawable.icons_whatsapp) {
                try {
                    val uri = Uri.parse("https://api.whatsapp.com/send?phone=${item.phoneNumber}")
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    launcher.launch(intent)
                } catch (e: Exception) {
                    navViewModal.enqueueSnackbar(e.message.toString())
                }
            }
            SocialIcon(R.drawable.icons_instagram){
                try {
                    val uri = Uri.parse("https://www.instagram.com/${item.insta}")
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    launcher.launch(intent)
                } catch (e: Exception) {
                    navViewModal.enqueueSnackbar(e.message.toString())
                }
            }
        }
    }
}

@Composable
fun GeneralEvent(heading: String,text:String)
{
    Column (Modifier.fillMaxWidth()){
        HeadingWithoutPadding(heading = heading, modifier = Modifier.padding(5.dp))
        HorizontalDivider()
//        Spacer(modifier = Modifier.height(5.dp))
        Text(text = text, maxLines = 3, fontSize = 12.sp, overflow = TextOverflow.Ellipsis, fontFamily = PoppinsFont, textAlign = TextAlign.Center,
            modifier=Modifier.padding(5.dp))
    }}
//@Preview
//@Composable
//fun GeneralPreview()
//{
//    GeneralEvent("Dhodhotsav","dafsdfasdfasdf asdfasdfa sfasd fasd f sadf asd fsd f asdf asdf asd f asdfasdfasd f asdf sdf dsfas d f s ad fsd af asdf asdf asdfsdfasdfas df asdf asdf asdf asdf asdf asdf sd sdfasd sd fa sd fasd f asdf asdf asd f asdf asdf asdf asd")
//}
@Composable
fun EventItem(item:EventClass,navViewModal:NavViewModel)
{
    if(item.type == "") return
    val containerBG = if(item.type.lowercase() == "wedding"){
        weedingContainerColor
    }
    else if(item.type.lowercase() == "birthday"){
        bdayContainerColor
    }
    else{
        generalContainerColor
    }

    Column {

        Row(
            Modifier
                .height(125.dp)
                .padding(end = 12.dp, start = 5.dp), verticalAlignment = Alignment.Bottom
        ) {
            Box(
                Modifier
                    .fillMaxHeight()
                    .zIndex(5f)
            ) {
                if(item.type.lowercase() != "general" ) {
                    Icon(
                        painter = painterResource(
                            id = if (item.type.lowercase() == "wedding") {
                                R.drawable.couple
                            } else if(item.type.lowercase() == "birthday"){
                                R.drawable.icons_cake
                            }
                            else{
                                R.drawable.icons_events
                            }
                        ),
                        tint = Color.Unspecified,
                        contentDescription = "",
                        modifier = Modifier
                            .size(30.dp)
                            .background(ribbenIconBGColor, shape = RoundedCornerShape(25.dp))
                            .padding(5.dp)
                    )
                }
                else{
                    Box(Modifier.width(30.dp)) {

                    }
                }
            }
            Row(
                Modifier
                    .height(100.dp)
                    .offset(y = (-10).dp)
                    .background(color = Color.Unspecified, shape = RoundedCornerShape(5.dp))
                    .fillMaxWidth(1f),

                ) {
                Row(
                    Modifier

                        .background(containerBG, shape = RoundedCornerShape(5.dp))
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
                                text = item.date.toString(),
                                textAlign = TextAlign.Center,
                                fontFamily = PoppinsFont,
                                fontWeight = FontWeight.Thin,
                                fontSize = 30.sp,
                            )
                            Text(item.month, fontFamily = PoppinsFont)
                        }
                        VerticalDivider()
                    }
                    Column(
                        Modifier
                            .fillMaxHeight()
                            .fillMaxWidth()
                            .offset(x = 10.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        if(item.type.lowercase()=="wedding")
                        {
                        WeddingTextContainer(item)
                        }
                        else if(item.type.lowercase()=="birthday"){
                            BirthdayTextItem(item, navViewModal = navViewModal )
                        }
                        else{
                            GeneralEvent(item.heading,item.description)
                        }
                    }
                }
            }
        }
        if (item.type.lowercase() != "birthday")
        {
            BelowBarWedding(item, navViewModal)
        }
    }
}

@Composable
fun BelowBarWedding(item: EventClass,navViewModal: NavViewModel)
{
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { }
    Row(
        Modifier
            .fillMaxWidth()
            .height(40.dp)
            .padding(horizontal = 25.dp)
            .offset(y = -8.dp)
            .background(weedingContainerColor, RoundedCornerShape(5.dp))
            .padding(horizontal = 10.dp, vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
        Text(item.name, fontFamily = PoppinsFont)
        Box{
            Row {
                SocialIcon(icon = R.drawable.icon_call){
                    val intent = Intent(Intent.ACTION_DIAL)
                    intent.data = Uri.parse("tel:${item.phoneNumber}")
                    launcher.launch(intent)
                }
                SocialIcon(icon = R.drawable.icons_whatsapp){
                    try {
                        val uri = Uri.parse("https://api.whatsapp.com/send?phone=${item.phoneNumber}")
                        val intent = Intent(Intent.ACTION_VIEW, uri)
                        launcher.launch(intent)
                    }
                    catch (e:Exception){
                        navViewModal.enqueueSnackbar(e.message.toString())
                    }
                }
                SocialIcon(icon = R.drawable.icons_download){
                    try {
                        val uri = Uri.parse(item.brochure)
                        val intent = Intent(Intent.ACTION_VIEW, uri)
                        launcher.launch(intent)
                    }
                    catch (e:Exception){
                        navViewModal.enqueueSnackbar(e.message.toString())
                    }
                }
            }
        }

    }
    Spacer(modifier = Modifier.height(3.dp))
    HorizontalDivider()
    Spacer(modifier = Modifier.height(3.dp))
}
@Composable
fun HeadingWithoutPadding(heading:String,modifier: Modifier)
{
    Text(text = heading, color = colorResource(id = R.color.textSimple),
        fontWeight = FontWeight.Medium, fontSize = 15.sp, textAlign = TextAlign.Center,
        fontFamily = PoppinsFont,
        modifier = modifier
            .fillMaxWidth(),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis)
}
@Composable
fun WeddingTextContainer(item:EventClass)
{
    HeadingWithoutPadding(
        heading = item.heading,
        Modifier.padding(top = 5.dp)
    )
    Text(
        "weds", fontFamily = SatisfyFont, modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp), fontSize = 22.sp, textAlign = TextAlign.Center
    )
    HeadingWithoutPadding(
        heading = item.description,
        Modifier.padding(bottom = 5.dp)
    )
}


@Composable
fun ShimmerAdsItem() {
    val shimmerColor = listOf(
        Color(0xFFBFDCF3), // Light color
        Color(0xFF78ABD3), // Dark color
        Color(0xFFB8D6EE)  // Light color again
    )

    // Shimmer animation parameters
    val transition = rememberInfiniteTransition(label = "")
    val shimmerTranslateAnimation by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1200, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    Column(Modifier.padding(10.dp)) {
        Box(
            modifier = Modifier
                .background(color = shopContainerColor, shape = RoundedCornerShape(5.dp))
                .height(100.dp)
                .fillMaxWidth()
        ) {
            Row (verticalAlignment = Alignment.CenterVertically){
                // Image with shimmer effect
                ShimmerPlaceholder(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(100.dp)
                        .clip(shape = RoundedCornerShape(5.dp, 0.dp, 0.dp, 5.dp))
                        .align(Alignment.CenterVertically),
                    colors = shimmerColor,
                    translateAnimation = shimmerTranslateAnimation
                )

                Column {
                    // Title with shimmer effect
                    ShimmerPlaceholder(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .height(25.dp),
                        colors = shimmerColor,
                        translateAnimation = shimmerTranslateAnimation
                    )

                    HorizontalDivider()

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Description with shimmer effect
                        ShimmerPlaceholder(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(7.dp)
                                .fillMaxHeight(0.5f),
                            colors = shimmerColor,
                            translateAnimation = shimmerTranslateAnimation
                        )
                    }
                }
            }
        }
        // Contact section with shimmer effect
        ShimmerPlaceholder(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 5.dp),
            colors = shimmerColor,
            translateAnimation = shimmerTranslateAnimation
        )
    }
}


//@Preview
//@Composable
//fun ShimmerAdsItemPreview()
//{
//    ShimmerAdsItem()
//}