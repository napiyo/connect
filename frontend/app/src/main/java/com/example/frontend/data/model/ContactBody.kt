package com.example.frontend.data.model

import com.google.gson.annotations.SerializedName

data class ContactBody(
    val name: String,
    val fatherName: String,
    val vans: String,
    val village: String,
    val phoneNumber: String
)