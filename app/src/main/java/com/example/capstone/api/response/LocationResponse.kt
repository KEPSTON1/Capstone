package com.example.capstone.api.response


data class StoreResponse(
    val toko: List<LocationResponse>
)
data class LocationResponse(
    val nama: String?,
    val alamat: String?,
    val rating: Double?,
    val fotoUrl: String?,
    val jamBuka: String?,
    val jamTutup: String?,
    val nomorHP: String?,
    val whatsappUrl: String?,
    val lokasi: Lokasi?
)

data class Lokasi(
    val latitude: Double,
    val longitude: Double
)