package com.example.frontend.data.model

data class AppConfigRes(
    val _id: String,
    val latestAppVer: Int,
    val mode: String,
    val requiredVer: Int
)