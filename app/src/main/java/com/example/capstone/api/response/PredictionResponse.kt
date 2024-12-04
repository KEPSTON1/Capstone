package com.example.capstone.api.response

data class PredictionResponse(
    val Kategori_Depresi: String,
    val Kategori_Kecemasan: String,
    val Kategori_Stres: String,
    val Prediksi_Depresi: Double,
    val Prediksi_Kecemasan: Double,
    val Prediksi_Stres: Double,
    val Skor_Depresi: Int,
    val Skor_Kecemasan: Int,
    val Skor_Stres: Int
)