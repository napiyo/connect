package com.example.frontend.screens

import android.content.Intent
import android.graphics.RectF
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
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import com.example.frontend.R
import com.example.frontend.appComponent.Header
import com.example.frontend.appComponent.HeaderWithDropDown
import com.example.frontend.appComponent.HeadingAppComponent
import com.example.frontend.appComponent.SocialIcon
import com.example.frontend.data.model.Shop
import com.example.frontend.data.repoImpl.ShopRepoImpl
import com.example.frontend.presentation.NavViewModel
import com.example.frontend.presentation.ShopsViewModel
import com.example.frontend.ui.theme.PoppinsFont
import com.example.frontend.ui.theme.Purple40
import com.example.frontend.ui.theme.shopContactColor
import com.example.frontend.ui.theme.shopContainerColor
import com.example.frontend.utils.configs
import com.example.frontend.utils.getToken
import com.example.frontend.utils.saveToken

@Composable
fun HomeScreen(navViewModal:NavViewModel,shopViewModel:ShopsViewModel) {
    val shopState by shopViewModel.shopsState.collectAsState()
    val shops by shopViewModel.shops.collectAsState()
    val isLoading by shopViewModel.isLoading.collectAsState()
    val retryCount by shopViewModel.retryCount.collectAsState()
    val villageSelected by navViewModal.village.collectAsState()
    val prevVillage by shopViewModel.prevVillage.collectAsState()

    LaunchedEffect(shopState)
    {
        if(!shopState.success && shopState.data.toString().isNotBlank())
        {
            navViewModal.enqueueSnackbar(shopState.data.toString())
        }
    }
    LaunchedEffect(retryCount)
    {
        if(retryCount== configs.MAX_RETRY)
        {
            navViewModal.enqueueSnackbar("we couldn't fetch shops, please retry")
        }
    }
    LaunchedEffect(villageSelected)
    {
       if(prevVillage.lowercase() != villageSelected.lowercase())
       {
        shopViewModel.clearShops()
       }

    }
    val context = LocalContext.current

    Scaffold ()
    {
        if(it.toString() == ""){}
        Column {
            HeaderWithDropDown(iconId = R.drawable.icons_shop, text = "Shops", navViewModal )
            HorizontalDivider()
            LazyColumn(Modifier.padding(start = 10.dp, bottom = 10.dp,end=10.dp)) {
                itemsIndexed(shops) { index, item ->
                    if(isLoading)
                    {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            for (i in 1..5)
                            {
                                ShimmerShopItem()
                            }
                        }
                    }
                    ShopItem(navViewModal,item)
                    if(!isLoading &&  shops.lastIndex == index){
                        if(retryCount >= configs.MAX_RETRY)
                        {
                            Text(text = "failed to get more shops", maxLines = 1, modifier = Modifier.padding(5.dp))
                        }
                        else{
                            shopViewModel.getShops(village = villageSelected)
                        }
                        if(shops.size==1){
                            Text("No Shops found", modifier = Modifier.padding(5.dp))
                        }
                    }
                }
            }
        }
    }
}
@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(NavViewModel(), ShopsViewModel(ShopRepoImpl()))
}

@Composable
fun ShopItem(navViewModal: NavViewModel,item:Shop) {
    if(item.shopName == "") return
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { }
    Column(Modifier.padding(vertical = 10.dp)) {
        Box(
            modifier = Modifier
                .background(color = shopContainerColor, shape = RoundedCornerShape(5.dp))
                .height(150.dp)
                .fillMaxWidth()
        ) {
            Row {
                SubcomposeAsyncImage(
                    model = item.imageURL,
                    loading = {
//                        CircularProgressIndicator(modifier = Modifier.size(35.dp))
                        val transition = rememberInfiniteTransition(label = "")
                        val shimmerTranslateAnimation by transition.animateFloat(
                            initialValue = 0f,
                            targetValue = 1000f,
                            animationSpec = infiniteRepeatable(
                                animation = tween(durationMillis = 1200, easing = LinearEasing),
                                repeatMode = RepeatMode.Reverse
                            ), label = ""
                        )
                        ShimmerPlaceholder(modifier = Modifier
                            .width(125.dp)
                            .height(150.dp), translateAnimation = shimmerTranslateAnimation)
                              },
                    error = {
                        Icon(
                            painter = painterResource(id = R.drawable.icons_image),
                            contentDescription = "failed image",
                            tint = Color.Unspecified,
                            modifier = Modifier.size(30.dp)
                        )
                    },
                    contentDescription = "shop image",
                    modifier = Modifier
                        .height(150.dp)
                        .width(125.dp)
                        .clip(shape = RoundedCornerShape(5.dp, 0.dp, 0.dp, 5.dp)),
                    alignment = Alignment.Center,
                    contentScale = ContentScale.Crop
                )

                Column {
                    Text(item.shopName, fontFamily = PoppinsFont, fontWeight = FontWeight.Medium, fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis)
                    HorizontalDivider()
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = item.description,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(7.dp),
                            textAlign = TextAlign.Center,
                            fontFamily = PoppinsFont,
                            fontSize = 12.sp,
                            maxLines = 4,
                            overflow = TextOverflow.Ellipsis

                        )
                        Column(
                            verticalArrangement = Arrangement.Bottom,
                            modifier = Modifier.fillMaxHeight()
                        ) {
                            HorizontalDivider()
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.icon_pin),
                                    contentDescription = "",
                                    modifier = Modifier.size(15.dp),
                                    tint = Color.Unspecified
                                )
                                Spacer(modifier = Modifier.width(3.dp))
                                Text(
                                    text = item.address,
                                    modifier = Modifier
                                        .padding(5.dp),
                                    textAlign = TextAlign.Center,
                                    fontFamily = PoppinsFont,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }
                }
            }
        }
        Row(
            Modifier
                .padding(horizontal = 10.dp)
                .background(
                    color = shopContactColor,
                    shape = RoundedCornerShape(bottomEnd = 5.dp, bottomStart = 5.dp)
                )
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween
            ){

            Text(text = item.name, fontFamily = PoppinsFont)
            Row {
                SocialIcon(icon = R.drawable.icons_whatsapp) {
                    try {
                        val uri = Uri.parse("https://api.whatsapp.com/send?phone=${item.phoneNumber}")
                        val intent = Intent(Intent.ACTION_VIEW, uri)
                        launcher.launch(intent)
                    }
                    catch (e:Exception){
                        navViewModal.enqueueSnackbar(e.message.toString())
                    }

                }
                SocialIcon(icon = R.drawable.icon_call){
                    val intent = Intent(Intent.ACTION_DIAL)
                    intent.data = Uri.parse("tel:${item.phoneNumber}")
                    launcher.launch(intent)
                }
            }
        }
    }
}


@Composable
fun ShimmerShopItem() {
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
                .height(150.dp)
                .fillMaxWidth()
        ) {
            Row {
                // Image with shimmer effect
                ShimmerPlaceholder(
                    modifier = Modifier
                        .height(150.dp)
                        .width(125.dp)
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

                        Column(
                            verticalArrangement = Arrangement.Bottom,
                            modifier = Modifier.fillMaxHeight()
                        ) {
                            HorizontalDivider()
                            // Address with shimmer effect
                            ShimmerPlaceholder(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(7.dp)
                                    .height(25.dp),
                                colors = shimmerColor,
                                translateAnimation = shimmerTranslateAnimation
                            )
                        }
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

@Composable
fun ShimmerPlaceholder(
    modifier: Modifier = Modifier,
    colors: List<Color> =  listOf(
    Color(0xFFBFDCF3), // Light color
    Color(0xFF78ABD3), // Dark color
    Color(0xFFB8D6EE)  // Light color again
),
    translateAnimation: Float
) {
    Box(
        modifier = modifier.background(
            brush = Brush.linearGradient(
                colors,
                start = Offset(translateAnimation - 1000f, 0f),
                end = Offset(translateAnimation, 0f),
                tileMode = TileMode.Mirror
            )
        )
    )
}

//@Preview
//@Composable
//fun shimmerSHop()
//{
//    ShimmerShopItem()
//}
