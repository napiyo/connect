package com.example.frontend.screens.auth

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.compose.rememberNavController
import com.example.frontend.appComponent.HeadingAppComponent
import com.example.frontend.appComponent.VillageSelectionDropdown
import com.example.frontend.data.model.Contact
import com.example.frontend.data.model.ContactBody
import com.example.frontend.data.model.phoneNumberClass
import com.example.frontend.data.repoImpl.AuthRepoImpl
import com.example.frontend.data.repoImpl.ContactsRepoImpl
import com.example.frontend.data.repoImpl.ServerConfigRepoImpl
import com.example.frontend.presentation.AuthViewModel
import com.example.frontend.presentation.ContactViewModel
import com.example.frontend.presentation.NavViewModel
import com.example.frontend.presentation.ServerConfigViewModel
import com.example.frontend.ui.theme.PoppinsFont
import com.example.frontend.ui.theme.buttonColor
import com.example.frontend.utils.Screens
import com.example.frontend.utils.getToken
import com.example.frontend.utils.saveToken

@Composable
fun ProfileLoginScreen(navController: NavController,token:String="",phoneNumberProp:String="",)
{
    val authViewModel = remember { AuthViewModel(AuthRepoImpl()) }
    val loginState by authViewModel.loginApiState.collectAsState()
    val isLoadingAuth by authViewModel.isLoading.collectAsState()
    val contactViewModel =  remember {ContactViewModel(ContactsRepoImpl())}
    val isLoadingContact by contactViewModel.isLoading.collectAsState()
    val contactState by contactViewModel.getContactState.collectAsState()
    var name by remember { mutableStateOf("") }
    var fName by remember { mutableStateOf("") }
    val navViewModel = remember { NavViewModel() }
    val configViewModel = remember { ServerConfigViewModel(ServerConfigRepoImpl()) }
    val villageList by configViewModel.villageState.collectAsState()
    val selectedVillage by navViewModel.village.collectAsState()
    var vans by remember { mutableStateOf("") }
    var isLoading   = isLoadingContact || isLoadingAuth
//    val context = LocalContext.current
    var errMsg by remember {
        mutableStateOf("")
    }

    LaunchedEffect(Unit)
    {
        configViewModel.getVillages()
        contactViewModel.getContact(phoneNumberProp)

    }

    LaunchedEffect(contactState)
    {
        if(contactState.success)
        {
            val tempContact = contactState.data as Contact
            name= tempContact.name
            fName=tempContact.fatherName
            vans=tempContact.vans
        }

    }
    LaunchedEffect(loginState)
    {
        if(loginState.success)
        {
//            saveToken(token)
            navController.navigate(Screens.MainScreen.route, builder = {
                popUpTo(Screens.MainScreen.route) { inclusive = true } // Set inclusive to true to also remove the destination itself
            })
        }
        else{
            saveToken("")
            errMsg = loginState.data.toString()
        }

    }
    LaunchedEffect(villageList)
    {
        if(villageList.success)
        {

            var temp = villageList.data as List<String>
            navViewModel.setVillagesList(temp)
            if(temp.size > 0){
                navViewModel.setVillage(temp[0])
            }
        }
        else{
            errMsg = villageList.data.toString()
        }
    }

    fun tryLogin(){
        saveToken(token)
        authViewModel.loginApi(ContactBody(name,fName,vans,selectedVillage,phoneNumberProp))
//        saveToken("")
    }
    Column(modifier = Modifier
        .fillMaxSize().background(Color.White)
        .padding(10.dp)) {
        HeadingAppComponent(heading = "Enter your Phone Number")
        HorizontalDivider()
        TextFiledCustom(value = name, _onValueChange = {it->name=it}, label = "Name", isLoading = isLoading)
        TextFiledCustom(value = fName, _onValueChange = {it->fName=it}, label = "Father Name", isLoading = isLoading)
        TextFiledCustom(value = vans, _onValueChange = {it->vans=it}, label = "Vans", isLoading = isLoading)
        VillageSelectionDropdown(navViewModel)

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = errMsg, fontSize = 12.sp, fontFamily = PoppinsFont, color = Color.Red,textAlign= TextAlign.Center,
            modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(10.dp))

        Button(
            shape = RoundedCornerShape(5.dp),
            colors = ButtonColors(containerColor = buttonColor, contentColor = Color.White, disabledContainerColor = Color.Gray, disabledContentColor = Color.Black),
            modifier = Modifier.fillMaxWidth(),
            onClick = {
//                authViewModel.loginApi(ContactBody(name,fName,vans,selectedVillage,phoneNumberProp))
                tryLogin()
            }) {
            if(isLoading){
                CircularProgressIndicator(color = Color.White)
            }
            else{
                Text(text = "Login", fontFamily = PoppinsFont, fontWeight = FontWeight.Medium)
            }
        }

    }

}
@Preview
@Composable
fun ProfileLoginScreenPreview()
{
    ProfileLoginScreen(navController = rememberNavController(),"","")
}

@Composable
fun TextFiledCustom(value: String,_onValueChange:(String)->Unit,label:String,isLoading:Boolean)
{
    OutlinedTextField(value = value, onValueChange = _onValueChange,
        label = { Text(text = label) },
        singleLine = true,
        textStyle = TextStyle(fontSize = 20.sp, fontFamily = PoppinsFont),
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .navigationBarsPadding()
            .imePadding(),
        enabled = !isLoading
    )
}