package com.example.frontend.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.frontend.R
import com.example.frontend.appComponent.HeadingAppComponent
import com.example.frontend.ui.theme.PoppinsFont
import com.example.frontend.ui.theme.Purple40
import com.example.frontend.ui.theme.shopContactColor
import com.example.frontend.ui.theme.shopContainerColor

@Composable
fun HomeScreen() {
    var list = listOf<Int>(1,2,3,4,5,6,7,8,9,10,11,12)
    Scaffold ()
    {
        if(it.toString() == ""){}
        Column {
            Row (horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(10.dp)){
                Icon(painter = painterResource(id = R.drawable.icons_shop), contentDescription = "", tint = Color.Unspecified,
                    modifier = Modifier.size(30.dp))
                Spacer(modifier = Modifier.width(5.dp))
                Text(text="Shops",
                    fontFamily = PoppinsFont, fontWeight = FontWeight.Medium, fontSize = 25.sp
                )
            }
            HorizontalDivider()
            LazyColumn(Modifier.padding(horizontal = 10.dp, vertical = 10.dp)) {
                itemsIndexed(list) { index, item ->
                    ShopItem()
                }
            }
        }
    }
}
@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}

@Composable
fun ShopItem() {
    val text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec egestas, tellus"
    Column(Modifier.padding(vertical = 10.dp)) {
        Box(
            modifier = Modifier
                .background(color = shopContainerColor, shape = RoundedCornerShape(5.dp))
                .height(150.dp)
                .fillMaxWidth()
        ) {
            Row {
                Image(
                    painter = painterResource(id = R.drawable.shop_dummy), contentDescription = "",
                    Modifier
                        .height(150.dp)
                        .width(125.dp)
                        .clip(shape = RoundedCornerShape(5.dp, 0.dp, 0.dp, 5.dp)),
                    contentScale = ContentScale.Crop
                )
                Column {
                    Text("Shree Mahakal Store", fontFamily = PoppinsFont, fontWeight = FontWeight.Medium, fontSize = 18.sp,
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
                            text = text,
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
                                    text = "Char Rasta,Sanchore",
                                    modifier = Modifier
                                        .padding(vertical = 5.dp),
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
            Text(text = "Narendra Dewasi", fontFamily = PoppinsFont)
            Icon(painter = painterResource(id = R.drawable.icon_call),contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = Color.Unspecified
            )
        }
    }
}