package com.example.capstone.ui.maps

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstone.api.response.LocationResponse
import com.example.capstone.pref.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MapsViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _locations = MutableLiveData<List<LocationResponse>>()
    val locations: LiveData<List<LocationResponse>> = _locations

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getNearbyLocations(
        latitude: Double = -6.2088,
        longitude: Double = 106.8456,
        keyword: String = "mental health di bali"
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = withContext(Dispatchers.IO) {
                    userRepository.getNearbyLocations(latitude, longitude, keyword)
                }
                _locations.value = response
                _errorMessage.value = null
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _errorMessage.value = e.message
                }
            } finally {
                _isLoading.value = false
            }
        }
    }
}