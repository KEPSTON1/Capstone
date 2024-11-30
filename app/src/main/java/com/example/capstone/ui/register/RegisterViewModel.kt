package com.example.capstone.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstone.pref.UserRepository
import kotlinx.coroutines.launch

class RegisterViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _registerResult = MutableLiveData<Boolean>()
    val registerResult: LiveData<Boolean> = _registerResult

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun register(
        email: String,
        firstName: String,
        lastName: String,
        password: String,
        phone: String,
        gender: String,
        age: Int
    ) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = userRepository.register(email, firstName, lastName, password, phone, gender, age)
                if (response.message == "Registrasi Akun Berhasil") {
                    _registerResult.value = true
                    _errorMessage.value = null
                } else {
                    _registerResult.value = false
                    _errorMessage.value = response.message
                }
            } catch (e: Exception) {
                _registerResult.value = false
                _errorMessage.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }
}