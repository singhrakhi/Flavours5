package com.flavours5.models

data class LoginResponse(
    val Data: Data,
    val Messege: String,
    val Status: String
)

data class Data(
    val Address: Any,
    val CreateDate: String,
    val Email: Any,
    val Id: Int,
    val Mobile: String,
    val Name: Any,
    val active: Boolean,
    val passwword: String,
    val username: String
)