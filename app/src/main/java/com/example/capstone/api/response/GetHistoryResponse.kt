package com.example.capstone.api.response

data class GetHistoryResponse(
    val history: List<HistoryItem>
)

data class HistoryItem(
    val created_at: String,
    val id: Int,
    val kategori_depresi: String,
    val kategori_kecemasan: String,
    val kategori_stres: String,
    val prediksi_depresi: Double,
    val prediksi_kecemasan: Double,
    val prediksi_stres: Double,
    val skor_depresi: Int,
    val skor_kecemasan: Int,
    val skor_stres: Int
)