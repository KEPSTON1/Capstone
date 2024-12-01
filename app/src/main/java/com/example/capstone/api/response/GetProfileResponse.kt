package com.example.capstone.api.response

data class GetProfileResponse(
    val success: Boolean,
    val user: User
)

data class User(
    val id_user: Int,
    val email: String,
    val first_name: String,
    val last_name: String,
    val password: String,
    val phone: String,
    val gender: String,
    val age: Int,
    val created_at: String,
    val updated_at: String
)