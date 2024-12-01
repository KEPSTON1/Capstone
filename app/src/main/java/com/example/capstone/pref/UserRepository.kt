package com.example.capstone.pref

import com.example.capstone.api.ApiService
import com.example.capstone.api.response.GetArticlesResponse
import com.example.capstone.api.response.GetProfileResponse
import com.example.capstone.api.response.LoginResponse
import com.example.capstone.api.response.RegisterResponse

class UserRepository(private val apiService: ApiService,
                     private val userPreferences: UserPreferences) {

    suspend fun register(
        email: String,
        firstName: String,
        lastName: String,
        password: String,
        phone: String,
        gender: String,
        age: Int
    ): RegisterResponse {
        return apiService.register(email, firstName, lastName, password, phone, gender, age)
    }

    suspend fun login(email: String, password: String): LoginResponse {
        return apiService.login(email, password)
    }

    suspend fun getProfile(token: String): GetProfileResponse {
        return apiService.getProfile()
    }

    suspend fun getArticles(token: String): GetArticlesResponse {
        return apiService.getArticles()
    }
}