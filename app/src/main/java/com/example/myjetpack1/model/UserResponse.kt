package com.example.myjetpack1.model

data class UserResponse(
    val results: List<User>,
    val info: Info
)

data class Info(
    val seed: String,
    val results: Int,
    val page: Int,
    val version: String
)