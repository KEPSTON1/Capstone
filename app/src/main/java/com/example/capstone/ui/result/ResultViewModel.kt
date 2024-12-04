package com.example.capstone.ui.result

import androidx.lifecycle.ViewModel
import com.example.capstone.api.ApiConfig
import com.example.capstone.api.response.PredictionResponse
import com.example.capstone.pref.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ResultViewModel(private val repository: UserRepository) : ViewModel() {

    suspend fun getPrediction(token: String, answers: HashMap<String, Int>): PredictionResponse {
        return withContext(Dispatchers.IO) {
            val mlApiService = ApiConfig.getMlApiService(token)
            mlApiService.predict(answers).execute().body()!!
        }
    }
}