package com.example.myjetpack1.model

data class User(
    val login: Login,
    val name: Name,
    val email: String,
    val picture: Picture
)

data class Login(
    val uuid: String
)

data class Name(
    val first: String,
    val last: String
)

data class Picture(
    val large: String,
    val medium: String,
    val thumbnail: String
)