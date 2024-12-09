package com.example.capstone.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstone.api.response.EditProfileRequest
import com.example.capstone.api.response.EditProfileResponse
import com.example.capstone.pref.UserRepository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditProfileViewModel(private val repository: UserRepository) : ViewModel() {

    private val _updateProfileResponse = MutableLiveData<EditProfileResponse>()
    val updateProfileResponse: LiveData<EditProfileResponse> = _updateProfileResponse

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun updateProfile(token: String, requestBody: EditProfileRequest) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = repository.updateProfile(token, requestBody)
                response.enqueue(object : Callback<EditProfileResponse> {
                    override fun onResponse(
                        call: Call<EditProfileResponse>,
                        response: Response<EditProfileResponse>
                    ) {
                        if (response.isSuccessful) {
                            _updateProfileResponse.value = response.body()
                        } else {
                            _errorMessage.value = response.message()
                        }
                        _isLoading.value = false
                    }

                    override fun onFailure(call: Call<EditProfileResponse>, t: Throwable) {
                        _errorMessage.value = t.message
                        _isLoading.value = false
                    }
                })
            } catch (e: Exception) {
                _errorMessage.value = e.message
                _isLoading.value = false
            }
        }
    }
}