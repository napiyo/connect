package com.example.frontend.screens

import android.annotation.SuppressLint
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
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
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.frontend.R
import com.example.frontend.appComponent.BottomSheet
import com.example.frontend.appComponent.Header
import com.example.frontend.appComponent.HeaderWithDropDown
import com.example.frontend.appComponent.HeadingAppComponent
import com.example.frontend.appComponent.SocialIcon
import com.example.frontend.data.model.Contact
import com.example.frontend.data.repoImpl.AuthRepoImpl
import com.example.frontend.data.repoImpl.ContactsRepoImpl
import com.example.frontend.presentation.AuthViewModel
import com.example.frontend.presentation.ContactViewModel
import com.example.frontend.presentation.NavViewModel
import com.example.frontend.ui.theme.PoppinsFont
import com.example.frontend.ui.theme.searchBarColor
import com.example.frontend.ui.theme.shimmerColor
import com.example.frontend.utils.configs.MAX_RETRY
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ContactsScreen(contactViewModel: ContactViewModel,contactViewModelForQuery:ContactViewModel,navViewModal:NavViewModel) {
    // ********** models *********
    val contactsState by contactViewModel.searchContactsState.collectAsState()
    val contacts by contactViewModel.contacts.collectAsState()
    val isLoading by contactViewModel.isLoading.collectAsState()
    val retryCount by contactViewModel.retryCount.collectAsState()
    val prevVillage by contactViewModel.prevVillage.collectAsState()
//    val contactViewModelForQuery = ContactViewModel(ContactsRepoImpl())
    val contactsStateForQuery by contactViewModelForQuery.searchContactsState.collectAsState()
    val contactsForQuery by contactViewModelForQuery.contacts.collectAsState()
    val isLoadingForQuery by contactViewModelForQuery.isLoading.collectAsState()
    val retryCountQuery by contactViewModelForQuery.retryCount.collectAsState()
    val prevVillageQuery by contactViewModelForQuery.prevVillage.collectAsState()
    val villageSelected by navViewModal.village.collectAsState()
    val scope = rememberCoroutineScope()
//    val showBottomSheet by remember { mutableStateOf(false) }
    val sheetSacffoldState = rememberBottomSheetScaffoldState()

    var selectedContact by remember { mutableStateOf<Contact?>(Contact(0,"",false,"","","",false,"","")) }
    LaunchedEffect(contactsState)
    {
        if(!contactsState.success && contactsState.data.toString().isNotBlank())
        {
            navViewModal.enqueueSnackbar(contactsState.data.toString())
        }
    }
    LaunchedEffect(contactsStateForQuery)
    {
        if(!contactsStateForQuery.success && contactsStateForQuery.data.toString().isNotBlank())
        {
            navViewModal.enqueueSnackbar(contactsStateForQuery.data.toString())
        }
    }
    LaunchedEffect(retryCount)
    {
        if(retryCount== MAX_RETRY)
        {
            navViewModal.enqueueSnackbar("we couldnt fetch contacts, please retry")
        }
    }
    LaunchedEffect(retryCountQuery)
    {
        if(retryCountQuery== MAX_RETRY)
        {
            navViewModal.enqueueSnackbar("we couldnt search contacts, please retry")
        }
    }
    LaunchedEffect(villageSelected)
    {
        if(prevVillage.lowercase() != villageSelected.lowercase()) {
            contactViewModel.clearContacts()
        }
        if(prevVillageQuery.lowercase() != villageSelected.lowercase()){
            contactViewModelForQuery.clearContacts()
        }
    }
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
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
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
        searchQuery = searchQueryTF.trim()
    }

    BottomSheetScaffold(sheetContent = { BottomSheet(selectedContact)},
        scaffoldState = sheetSacffoldState,
        sheetPeekHeight = 0.dp,
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
    ) { it ->
        if(it.toString()==""){}
        Column(modifier = Modifier
            .fillMaxHeight()
//            .padding(5.dp)
        ) {
            HeaderWithDropDown(R.drawable.icon_contact,"Contacts", navViewModal)
//            BottomSheet(sheetState = sheetState, scope = scope ){it-> showBottomSheet = it}
            TextField(
                value = searchQueryTF, onValueChange = { it-> searchQueryTF = it },
                placeholder = { Text(text = "Search Contacts") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
                    .focusRequester(focusRequester),
                textStyle = TextStyle(fontFamily = PoppinsFont, fontSize = 18.sp),
                trailingIcon = {
                    if(searchQuery.isEmpty()) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_search),
                            contentDescription = "search contacts",
                            Modifier
                                .size(30.dp)
                                .clickable {
                                    changeSearching(true)
                                },
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
                    try {
                    kc?.hide()
                        focusManager.clearFocus()
                    }catch (e:Exception)
                    {
                        Log.e("narenda",e.message.toString())
                    }
                }),


            )
            if(searching)
            {
                Text(text = "search results for \"${searchQuery}\" in ${villageSelected}", maxLines = 1, overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(5.dp))
            }
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
                        ContactlistItem(item,navViewModal){
                            selectedContact = item
                            scope.launch {
                                sheetSacffoldState.bottomSheetState.expand()
                            }
                        }
                        HorizontalDivider()
                    }
                    if (isLoadingForQuery || isLoading) {
                        Column(
//                            horizontalAlignment = Alignment.Center,
                            modifier = Modifier
                                .fillMaxSize()
//                                .height(100.dp)
                        ) {
                            for (i in 1..10)
                            {
                                ShimmerLoadingItem()
                            }
                        }
                    }


                         if(searching && !isLoadingForQuery &&  contactsForQuery.lastIndex == index )
                        {
                            if(retryCountQuery >= MAX_RETRY)
                            {
                                Text(text = "failed to search more contacts", maxLines = 1, modifier = Modifier.padding(5.dp))
                            }
                            else if (searchQuery.length == 1) {
                                contactViewModelForQuery.searchContacts(startsWith = searchQuery, village = villageSelected)
                            } else {
                                contactViewModelForQuery.searchContacts(name = searchQuery,village = villageSelected)
                            }
                            if(contactsForQuery.size==1){
                                Text("No Contacts found", modifier = Modifier.padding(5.dp))
                            }
                        }


                        else if(!searching && !isLoading &&  contacts.lastIndex == index){
                             if(retryCount >= MAX_RETRY)
                             {
                                 Text(text = "failed to get more contact", maxLines = 1, modifier = Modifier.padding(5.dp))
                             }
                             else{
                                contactViewModel.searchContacts(village = villageSelected)
                             }
                             if(contacts.size==1){
                                 Text("No Contacts found", modifier = Modifier.padding(5.dp))
                             }
                        }
                }
            }
            }
        }

//    }
}
@Composable
fun ContactlistItem(item:Contact,navViewModal:NavViewModel,onClicked:()->Unit){
    if(item.phoneNumber == "") return
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { }
    ListItem(
        headlineContent = { Text(text = item.name, fontFamily = PoppinsFont) },
        supportingContent = { Text(text = item.phoneNumber, fontFamily = PoppinsFont) },
        trailingContent = {
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
                SocialIcon(icon = R.drawable.icon_call) {
                    val intent = Intent(Intent.ACTION_DIAL)
                    intent.data = Uri.parse("tel:${item.phoneNumber}")
                    launcher.launch(intent)
                }
            }

        },
        leadingContent = {
            Icon(painter = painterResource(id = R.drawable.icon_user), contentDescription = "user profile",  tint = Color.Unspecified)
        },
        modifier = Modifier.clickable(indication = rememberRipple(bounded = true), interactionSource =  remember { MutableInteractionSource() }){
            onClicked()
        }

    )

}
@Preview
@Composable
fun ContactScreenPreview() {
    ContactsScreen(ContactViewModel(ContactsRepoImpl()),ContactViewModel(ContactsRepoImpl()),NavViewModel())
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
        .background(
            color = Color.White,
            shape = RoundedCornerShape(5.dp)
        )) {
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
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    Color.LightGray.copy(alpha = 0.4f),
                                    Color.LightGray.copy(alpha = 1f),
                                    Color.LightGray.copy(alpha = 0.4f)
                                ),
                                start = Offset(shimmerTranslateAnim - 400, 0f),
                                end = Offset(shimmerTranslateAnim, 0f)
                            ), shape = RoundedCornerShape(3.dp)
                        )
                        .width(150.dp)
                        .height(30.dp)) {

                }
            }
        }
    }
}
