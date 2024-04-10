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
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.frontend.R
import com.example.frontend.appComponent.HeadingAppComponent
import com.example.frontend.data.model.Contact
import com.example.frontend.data.repoImpl.AuthRepoImpl
import com.example.frontend.data.repoImpl.ContactsRepoImpl
import com.example.frontend.presentation.AuthViewModel
import com.example.frontend.presentation.ContactViewModel
import com.example.frontend.ui.theme.PoppinsFont
import com.example.frontend.ui.theme.searchBarColor
import com.example.frontend.ui.theme.shimmerColor
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ContactsScreen(contactViewModel: ContactViewModel) {
    // ********** models *********
    val contactsState by contactViewModel.searchContactsState.collectAsState()
    val contacts by contactViewModel.contacts.collectAsState()
    val isLoading by contactViewModel.isLoading.collectAsState()
    val contactViewModelForQuery = ContactViewModel(ContactsRepoImpl())
    val contactsStateForQuery by contactViewModelForQuery.searchContactsState.collectAsState()
    val contactsForQuery by contactViewModelForQuery.contacts.collectAsState()
    val isLoadingForQuery by contactViewModelForQuery.isLoading.collectAsState()

    var searchQuery by remember {
        mutableStateOf("")
    }
    var searchQueryTF by remember {
        mutableStateOf("")
    }
    var searching by remember {
        mutableStateOf(false)
    }
    val kc = LocalSoftwareKeyboardController.current
    fun changeSearching(change:Boolean){
        if(searching)
        {
            if(searchQuery.isBlank())
            {
                searching = false
            }
            if(!change) {
                searching = false
            }
            if(searchQuery == searchQueryTF && change)
            {
                return
            }
            contactViewModelForQuery.clearContacts()
        }
        else if(change) {
            searching = searchQueryTF.isNotBlank()
        }
        searchQuery = searchQueryTF
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
    ) { it ->
        if(it.toString()==""){}
        Column(modifier = Modifier
            .fillMaxHeight()
            .padding(5.dp)) {
            Header()
            TextField(
                value = searchQueryTF, onValueChange = { it-> searchQueryTF = it },
                placeholder = { Text(text = "Search Contacts") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                textStyle = TextStyle(fontFamily = PoppinsFont, fontSize = 18.sp),
                trailingIcon = {
                    if(searchQuery.isEmpty()) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_search),
                            contentDescription = "search contacts",
                            Modifier.size(30.dp),
                            tint = Color.Unspecified

                        )
                    } else{
                        Icon(
                            painter = painterResource(id = R.drawable.icon_cancel),
                            contentDescription = "search contacts",
                            Modifier
                                .size(30.dp)
                                .clickable {
                                    searchQueryTF = ""
                                    changeSearching(false)
                                },
                            tint = Color.Unspecified,
                            )
                    }
                },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = searchBarColor,
                    cursorColor = Color.Black,
                    disabledLabelColor = Color.Red,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                singleLine = true,
                keyboardActions = KeyboardActions(onDone = {
                    changeSearching(true)
                    kc?.hide()
                })
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 5.dp)
            ) {
                itemsIndexed(
                    if(searching)
                    {
                        contactsForQuery
                    }
                    else{
                        contacts
                    }
                ) { index, item ->
                    Column {
                        ContactlistItem(item)
                        HorizontalDivider()
                    }
                    if (isLoadingForQuery || isLoading) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .fillMaxSize()
                                .height(100.dp)
                        ) {
                            ShimmerLoadingItem()
                        }
                    }
                        if(searching && !isLoadingForQuery &&  contactsForQuery.lastIndex == index )
                        {
                            if (searchQuery.length == 1) {
                                contactViewModelForQuery.searchContacts(startsWith = searchQuery)
                            } else {
                                contactViewModelForQuery.searchContacts(name = searchQuery)
                            }
                        }
                        else if(!searching && !isLoading &&  contacts.lastIndex == index){
                            contactViewModel.searchContacts()
                        }
                }
            }
            }
        }

//    }
}
@Composable
fun ContactlistItem(item:Contact){
    if(item.phoneNumber == "") return
    val makeCall = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { }
    ListItem(
        headlineContent = { Text(text = item.name, fontFamily = PoppinsFont) },
        supportingContent = { Text(text = item.phoneNumber, fontFamily = PoppinsFont) },
        trailingContent = {
            Icon(painter = painterResource(id = R.drawable.icon_call), contentDescription ="call user",
                modifier = Modifier
                    .size(30.dp)
                    .clickable {
                        val intent = Intent(Intent.ACTION_DIAL)
                        intent.data = Uri.parse("tel:${item.phoneNumber}")
                        makeCall.launch(intent)
                    },
                tint = Color.Unspecified
            )

        },
        leadingContent = {
            Icon(painter = painterResource(id = R.drawable.icon_user), contentDescription = "user profile",  tint = Color.Unspecified)
        },
        modifier = Modifier.clickable(indication = rememberRipple(bounded = true), interactionSource =  remember { MutableInteractionSource() }){}

    )

}
@Preview
@Composable
fun ContactScreenPreview() {
    ContactsScreen(ContactViewModel(ContactsRepoImpl()))
}

@Composable
fun Header(){
    Row (horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(10.dp)){
        Icon(painter = painterResource(id = R.drawable.icon_contact), contentDescription = "", tint = Color.Unspecified,
            modifier = Modifier.size(30.dp))
        Spacer(modifier = Modifier.width(5.dp))
        Text(text="Contacts",
            fontFamily = PoppinsFont, fontWeight = FontWeight.Medium, fontSize = 25.sp
        )
    }
    HorizontalDivider()
    Spacer(modifier = Modifier.height(5.dp))
}
@Composable
fun ShimmerLoadingItem() {
    val transition = rememberInfiniteTransition(label = "")
    val shimmerTranslateAnim by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            tween(durationMillis = 1500, easing = LinearEasing),
            RepeatMode.Reverse
        ), label = ""
    )

    val colors = listOf(
        shimmerColor.copy(alpha = 0.4f),
        shimmerColor.copy(alpha = 1f),
        shimmerColor.copy(alpha = 0.4f)
    )

    Box(modifier = Modifier
        .padding(6.dp)
        .background(color = Color.White,
            shape = RoundedCornerShape(5.dp))) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(72.dp)
                .background(
                    brush = Brush.linearGradient(
                        colors = colors,
                        start = Offset(shimmerTranslateAnim - 400, 0f),
                        end = Offset(shimmerTranslateAnim, 0f)
                    )
                ),
            contentAlignment = Alignment.CenterStart,

        ){
            Row(modifier = Modifier
                .width(300.dp)
                .padding(horizontal = 10.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically) {
                Box(
                    Modifier
                        .background(color = Color.LightGray, shape = RoundedCornerShape(25.dp))
                        .width(50.dp)
                        .height(50.dp)) {

                }
                Spacer(modifier = Modifier.width(10.dp))
                Box(
                    Modifier
                        .background( brush = Brush.linearGradient(
                            colors = listOf(
                                Color.LightGray.copy(alpha = 0.4f),
                                Color.LightGray.copy(alpha = 1f),
                                Color.LightGray.copy(alpha = 0.4f)
                            ),
                            start = Offset(shimmerTranslateAnim - 400, 0f),
                            end = Offset(shimmerTranslateAnim, 0f)
                        ), shape = RoundedCornerShape(3.dp))
                        .width(150.dp)
                        .height(30.dp)) {

                }
            }
        }
    }
}
