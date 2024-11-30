package com.example.capstone.api.response

data class LoginResponse(
    val success: Boolean,
    val message: String,
    val currUser: Int,
    val token: String
)
