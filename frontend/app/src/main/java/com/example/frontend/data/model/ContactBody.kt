package com.example.frontend.data.model

import com.google.gson.annotations.SerializedName

data class Contacts(
    @SerializedName("data")
    val data: List<Contact>
)