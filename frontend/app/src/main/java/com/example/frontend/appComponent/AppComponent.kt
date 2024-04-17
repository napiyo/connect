package com.example.frontend.appComponent



import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp

import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetState
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.frontend.R
import com.example.frontend.data.model.Contact
import com.example.frontend.presentation.NavViewModel
import com.example.frontend.screens.HeadingWithoutPadding
import com.example.frontend.ui.theme.PoppinsFont
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Locale


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
            CircularProgressIndicator(color = Color.White)
        }else{
            Icon(icon, contentDescription = iconDescription)
        }
    }
}

//@Preview
//@Composable
//fun FloatingActionButtonAppComponentPreview(){
//    FloatingActionButtonAppComponent(Icons.Outlined.ArrowForward,
//        iconDescription = "next",
//    ){}
//}

@Composable
fun HeadingAppComponent(heading:String){
    Text(text = heading, color = colorResource(id = R.color.textSimple),
        fontWeight = FontWeight.Medium, fontSize = 20.sp, textAlign = TextAlign.Center,
        fontFamily = PoppinsFont,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 5.dp), maxLines = 1)
}
//@Preview
//@Composable
//fun HeadingAppComponentPreview(){
//    HeadingAppComponent(heading = "Heading Example")
//}


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

//@Preview
//@Composable
//fun OverlayLoadingScreenPreview() {
//    OverlayLoadingScreen()
//}


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
//@Preview
//@Composable
//fun NavigationPreview() {
//    NavigationBarBottom(navViewModel = NavViewModel())
//}
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

@Composable
fun HeaderWithDropDown(iconId:Int,text:String,navViewModal:NavViewModel)
{
    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Bottom) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(10.dp)
        ) {
            Icon(
                painter = painterResource(id = iconId),
                contentDescription = "",
                tint = Color.Unspecified,
                modifier = Modifier.size(30.dp)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = text,
                fontFamily = PoppinsFont, fontWeight = FontWeight.Medium, fontSize = 25.sp
            )
        }
        VillageSelectionDropdown(navViewModal)
    }
}

//@Preview
//@Composable
//fun DropDownPreview()
//{
//    HeaderWithDropDown(R.drawable.icons_events,"Events", NavViewModel())
//}

@Composable
fun VillageSelectionDropdown(navViewModal:NavViewModel) {
    var expanded by remember { mutableStateOf(false) }
    val villageList by navViewModal.villageList.collectAsState()
    val villageSelected by navViewModal.village.collectAsState()

    

    Column(modifier = Modifier
        .clickable {
            expanded = !expanded
        }
//        .widthIn(150.dp)
//        .background(Color.White)
        .padding(end = 10.dp), horizontalAlignment = Alignment.End) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.height(40.dp), horizontalArrangement = Arrangement.End) {
            Icon(painter = painterResource(id = R.drawable.icons_viilage), contentDescription ="",tint= Color.Unspecified, modifier = Modifier.size(25.dp) )
            Text(
                text = villageSelected.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() },
                fontFamily = PoppinsFont,
                fontWeight = FontWeight.Medium, textAlign = TextAlign.Center, fontSize = 15.sp,
                modifier = Modifier.padding(start=5.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Icon(imageVector = if(expanded){Icons.Default.KeyboardArrowUp}else{Icons.Default.KeyboardArrowDown}, contentDescription ="" )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            offset = DpOffset(0.dp,0.dp),
            modifier = Modifier.height(300.dp)
        ) {
            villageList.forEach { village ->
                DropdownMenuItem(
                    onClick = {
                        navViewModal.setVillage(villageSelected = village)
                        expanded = false
                    },
                text={
                    Text(text = village)
                },
                    trailingIcon = { if(village==villageSelected){Icon(imageVector = Icons.Default.Check, contentDescription ="" )}},
                    modifier=Modifier.background(color = if(village==villageSelected) {
                        Color.LightGray
                    }else{
                        Color.Unspecified
                    }
                    )
                    )

            }
        }
    }
}

@Composable
fun SocialIcon(icon:Int, onClick:(String)->Unit)
{
    Icon(painter = painterResource(icon), contentDescription ="",tint= Color.Unspecified,
        modifier = Modifier
//            .padding(horizontal = 5.dp)
            .clickable { onClick("") }
            .size(30.dp))
}


@Composable
fun BottomSheet(contact: Contact?) {
    if(contact == null) return
    Column (Modifier.padding(10.dp)){
        HeadingAppComponent(heading = contact.name)
        Text(text = contact.phoneNumber, textAlign =  TextAlign.Center, modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 5.dp))
        HorizontalDivider(Modifier.padding(bottom = 5.dp))
        Row(verticalAlignment = Alignment.CenterVertically){
            Text(text = "Father name : ", fontSize = 11.sp)
            Text (text = " ${contact.fatherName}", fontFamily = PoppinsFont, fontWeight = FontWeight.Medium)
        }
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween
        , modifier = Modifier.fillMaxWidth()){
            Row(verticalAlignment = Alignment.CenterVertically){
            Text(text = "ganv : ", fontSize = 11.sp)
            Text(text = contact.village,
                fontFamily = PoppinsFont,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp
            )
            }
            Row(verticalAlignment = Alignment.CenterVertically)
            {
                Text(text = "vans : ", fontSize = 11.sp)
                Text(text = contact.vans, fontFamily = PoppinsFont, fontWeight = FontWeight.Medium)
            }
        }
            Spacer(modifier = Modifier.height(75.dp))


    }
}
@Preview
@Composable
fun BottomSheetPreview()
{
    BottomSheet(contact = Contact(0,"",false,"Sanwala ram Dewasi","Narendra Kumar Dewasi","7976224104",false,"Dewasi","amli"))
}
