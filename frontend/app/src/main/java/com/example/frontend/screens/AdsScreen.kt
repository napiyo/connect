package com.example.frontend.screens

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun AdsScreen() {
    Scaffold ()
    {
        if(it.toString() == "") {}
        Text(text = "Ads Screen")
    }
}
@Preview
@Composable
fun AdsScreenPreview() {
    AdsScreen()
}