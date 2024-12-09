package com.example.capstone.pref

import com.example.capstone.api.ApiService
import com.example.capstone.api.MlApiService
import com.example.capstone.api.response.DeleteHistoryResponse
import com.example.capstone.api.response.EditProfileRequest
import com.example.capstone.api.response.EditProfileResponse
import com.example.capstone.api.response.GetArticlesResponse
import com.example.capstone.api.response.GetHistoryResponse
import com.example.capstone.api.response.GetProfileResponse
import com.example.capstone.api.response.LocationResponse
import com.example.capstone.api.response.LoginResponse
import com.example.capstone.api.response.RegisterResponse
import retrofit2.Call

class UserRepository(
    private val apiService: ApiService,
    private val mlApiService: MlApiService,
    private val userPreferences: UserPreferences,
) {

    suspend fun register(
        email: String,
        firstName: String,
        lastName: String,
        password: String,
        phone: String,
        gender: String,
        age: Int,
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

    suspend fun getNearbyLocations(latitude: Double, longitude: Double, keyword: String): List<LocationResponse> {
        val response = apiService.getNearbyLocations(latitude, longitude, keyword)
        return response.toko
    }

    fun updateProfile(token: String, requestBody: EditProfileRequest): Call<EditProfileResponse> {
        return apiService.updateProfile(requestBody)
    }

    suspend fun getHistory(token: String): GetHistoryResponse {
        return mlApiService.getHistory()
    }

    suspend fun deleteHistory(id: Int, token: String): DeleteHistoryResponse {
        return mlApiService.deleteHistory(id)
    }
}