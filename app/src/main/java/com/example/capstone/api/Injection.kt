package com.example.capstone.api

import android.content.Context
import com.example.capstone.pref.UserPreferences
import com.example.capstone.pref.UserRepository

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val userPreferences = UserPreferences(context)
        val token = userPreferences.getSession().token
        val apiService = ApiConfig.getApiService(token)
        val mlApiService = ApiConfig.getMlApiService(token)
        return UserRepository(apiService, mlApiService, userPreferences)
    }
}