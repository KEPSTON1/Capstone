package com.example.capstone.pref

import android.content.Context
import com.example.capstone.api.response.LoginResponse

class UserPreferences(context: Context) {

    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun saveSession(loginResponse: LoginResponse) {
        preferences.edit()
            .putString(TOKEN_KEY, loginResponse.token)
            .putBoolean(IS_LOGIN_KEY, loginResponse.success)
            .putInt(CURR_USER_KEY, loginResponse.currUser)
            .apply()
    }

    fun getSession(): UserSession {
        val token = preferences.getString(TOKEN_KEY, "") ?: ""
        val isLogin = preferences.getBoolean(IS_LOGIN_KEY, false)
        val currUser = preferences.getInt(CURR_USER_KEY, 0)
        return UserSession(token, isLogin, currUser)
    }

    fun logout() {
        preferences.edit()
            .clear()
            .apply()
    }

    companion object {
        private const val PREFS_NAME = "user_prefs"
        private const val TOKEN_KEY = "token"
        private const val IS_LOGIN_KEY = "is_login"
        private const val CURR_USER_KEY = "curr_user"
    }
}

data class UserSession(
    val token: String,
    val isLogin: Boolean,
    val currUser: Int
)