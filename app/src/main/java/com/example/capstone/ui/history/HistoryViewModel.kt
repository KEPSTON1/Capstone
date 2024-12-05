package com.example.capstone.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstone.api.response.HistoryItem
import com.example.capstone.pref.UserRepository
import kotlinx.coroutines.launch

class HistoryViewModel(private val repository: UserRepository) : ViewModel() {

    private val _history = MutableLiveData<List<HistoryItem>>()
    val history: LiveData<List<HistoryItem>> = _history

    private lateinit var token: String

    fun setToken(token: String) {
        this.token = token
    }

    fun getHistory() {  // Hapus parameter token
        viewModelScope.launch {
            try {
                val response = repository.getHistory(token) // Gunakan token yang disimpan
                _history.value = response.history
            } catch (e: Exception) {
                // Tangani error
            }
        }
    }

    fun deleteHistory(id: Int) {  // Hapus parameter token
        viewModelScope.launch {
            try {
                repository.deleteHistory(id, token) // Gunakan token yang disimpan
                // Perbarui data history di LiveData
                val currentHistory = _history.value.orEmpty().toMutableList()
                currentHistory.removeIf { it.id == id }
                _history.value = currentHistory
            } catch (e: Exception) {
                // Tangani error
            }
        }
    }
}