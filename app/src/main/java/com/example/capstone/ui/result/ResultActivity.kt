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

                    binding.resultText.text = "Hasil Tes Anda Menandakan\nKategori Depresi: ${predictionResponse.Kategori_Depresi}\n" +
                            "Kategori Kecemasan: ${predictionResponse.Kategori_Kecemasan}\n" +
                            "Kategori Stres: ${predictionResponse.Kategori_Stres}"

                    binding.descriptionText.text = getDepressionDescription(predictionResponse.Kategori_Depresi) + "\n\n" +
                            getAnxietyDescription(predictionResponse.Kategori_Kecemasan) + "\n\n" +
                            getStressDescription(predictionResponse.Kategori_Stres)
                } catch (e: Exception) {

                    Log.e("ResultActivity", "Error: ${e.message}")
                    Toast.makeText(this@ResultActivity, "Gagal mendapatkan prediksi", Toast.LENGTH_SHORT).show()
                }
            }
        } else {

            Toast.makeText(this, "Data jawaban tidak valid", Toast.LENGTH_SHORT).show()
        }

        binding.continueButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        supportActionBar?.hide()
    }

    private fun getDepressionDescription(category: String): String {
        return when (category) {
            "Normal" -> "Tidak menunjukkan gejala depresi yang signifikan. Anda merasa nyaman dengan kehidupan sehari-hari dan memiliki pandangan yang positif."
            "Ringan" -> "Anda mungkin mengalami sedikit penurunan suasana hati atau motivasi, tetapi gejala ini tidak mengganggu aktivitas sehari-hari."
            "Sedang" -> "Ada perasaan sedih atau kehilangan minat yang lebih sering muncul. Gejala ini dapat mulai memengaruhi produktivitas dan hubungan Anda."
            "Parah" -> "Perasaan sedih atau kehilangan harapan yang mendalam. Aktivitas sehari-hari menjadi sulit dilakukan, dan dukungan mungkin sangat diperlukan."
            "Sangat Parah" -> "Gejala depresi yang sangat membebani, termasuk perasaan tidak berharga atau putus asa. Bantuan profesional sangat dianjurkan."
            else -> ""
        }
    }

    private fun getAnxietyDescription(category: String): String {
        return when (category) {
            "Normal" -> "Anda merasa tenang dan mampu mengelola tekanan sehari-hari dengan baik. Tidak ada tanda kecemasan yang signifikan."
            "Ringan" -> "Anda mungkin merasa sedikit gelisah atau cemas dalam situasi tertentu, tetapi hal ini tidak mengganggu rutinitas."
            "Sedang" -> "Rasa cemas sering muncul dan mulai memengaruhi konsentrasi atau aktivitas harian Anda. Mungkin ada perasaan gugup atau kekhawatiran yang berlebihan."
            "Parah" -> "Tingkat kecemasan yang tinggi, sering disertai gejala fisik seperti jantung berdebar atau sesak napas. Situasi sehari-hari menjadi sulit dikelola."
            "Sangat Parah" -> "Rasa cemas yang sangat intens dan konstan, mungkin mengarah pada serangan panik atau mengganggu fungsi kehidupan. Bantuan profesional sangat dianjurkan."
            else -> ""
        }
    }

    private fun getStressDescription(category: String): String {
        return when (category) {
            "Normal" -> "Anda merasa rileks dan mampu mengelola tantangan sehari-hari tanpa merasa terbebani."
            "Ringan" -> "Anda mengalami tekanan yang rendah, tetapi masih dalam batas yang dapat dikelola tanpa memengaruhi keseharian Anda."
            "Sedang" -> "Anda merasa stres lebih sering dari biasanya, yang dapat mengganggu produktivitas atau hubungan Anda dengan orang lain."
            "Parah" -> "Tingkat stres yang tinggi menyebabkan kesulitan dalam menangani aktivitas harian. Gejala fisik seperti sakit kepala atau kelelahan mungkin muncul."
            "Sangat Parah" -> "Stres yang sangat berat memengaruhi kesehatan mental dan fisik secara signifikan. Dukungan dari ahli diperlukan untuk mengatasinya."
            else -> ""
        }
    }
}