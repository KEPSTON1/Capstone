package com.example.capstone.api

import com.example.capstone.api.response.GetArticlesResponse
import com.example.capstone.api.response.GetProfileResponse
import com.example.capstone.api.response.LoginResponse
import com.example.capstone.api.response.RegisterResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("email") email: String,
        @Field("first_name") firstname: String,
        @Field("last_name") lastName: String,
        @Field("password") password: String,
        @Field("phone") phone: String,
        @Field("gender") gender: String,
        @Field("age") age: Int
    ): RegisterResponse

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @GET("profile")
    suspend fun getProfile(): GetProfileResponse

    @GET("article")
    suspend fun getArticles(): GetArticlesResponse
}