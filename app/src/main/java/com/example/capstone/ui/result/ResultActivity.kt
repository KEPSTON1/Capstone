package com.example.capstone.ui.result

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.capstone.MainActivity
import com.example.capstone.ResultViewModelFactory
import com.example.capstone.api.Injection
import com.example.capstone.databinding.ActivityResultBinding
import com.example.capstone.pref.UserPreferences
import kotlinx.coroutines.launch

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    private val viewModel by viewModels<ResultViewModel> {
        ResultViewModelFactory(Injection.provideRepository(this))
    }

    companion object {
        const val EXTRA_ANSWERS = "extra_answers"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val answers = intent.getSerializableExtra(EXTRA_ANSWERS) as? HashMap<String, Int>
        if (answers != null) {
            val userPreferences = UserPreferences(this)
            val userSession = userPreferences.getSession()

            lifecycleScope.launch {
                try {
                    val predictionResponse = viewModel.getPrediction(userSession.token, answers)
                    // Tampilkan hasil prediksi
                    binding.descriptionText.text = "Kategori Depresi: ${predictionResponse.Kategori_Depresi}\n" +
                            "Kategori Kecemasan: ${predictionResponse.Kategori_Kecemasan}\n" +
                            "Kategori Stres: ${predictionResponse.Kategori_Stres}"
                } catch (e: Exception) {
                    // Tangani error
                    Log.e("ResultActivity", "Error: ${e.message}")
                    Toast.makeText(this@ResultActivity, "Gagal mendapatkan prediksi", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            // Tangani jika data jawaban tidak valid
            Toast.makeText(this, "Data jawaban tidak valid", Toast.LENGTH_SHORT).show()
        }

        binding.continueButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        supportActionBar?.hide()
    }
}