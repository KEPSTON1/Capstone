package com.example.capstone.api.response

data class EditProfileRequest(
    val first_name: String,
    val last_name: String,
    val phone: String,
    val gender: String,
    val age: Int
)