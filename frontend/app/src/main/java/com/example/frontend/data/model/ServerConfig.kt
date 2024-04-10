package com.example.frontend.data.model

data class ServerConfig(
    val _id: String,
    val latestAppVer: Int,
    val mode: String,
    val requiredVer: Int
)