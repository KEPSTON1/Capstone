package com.example.capstone.ui.kuisioner

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.capstone.databinding.ActivityKuisionerBinding
import com.example.capstone.ui.result.ResultActivity
import com.google.android.material.button.MaterialButton

class KuisionerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityKuisionerBinding
    private var selectedAnswerButton: MaterialButton? = null

    private val questions = listOf(
        "Menjadi marah karena hal-hal kecil/sepele",
        "Mulut terasa kering",
        "Tidak dapat melihat hal yang positif dari suatu kejadian",
        "Merasakan gangguan dalam bernapas (napas cepat, sulit bernapas)",
        "Merasa sepertinya tidak kuat lagi untuk melakukan suatu kegiatan",
        "Cenderung bereaksi berlebihan pada situasi",
        "Kelemahan pada anggota tubuh",
        "Kesulitan untuk relaksasi/bersantai",
        "Cemas yang berlebihan dalam suatu situasi namun bisa lega jika hal/situasi itu berakhir",
        "Merasa pesimis dalam segala hal",
        "Mudah merasa kesal",
        "Merasa banyak menghabiskan energi karena cemas",
        "Merasa sedih dan depresi",
        "Tidak sabaran",
        "Merasa kelelahan",
        "Kehilangan minat pada banyak hal (misal: makan, ambulasi, sosialisasi)",
        "Merasa diri tidak layak",
        "Mudah tersinggung",
        "Berkeringat (misal: tangan berkeringat) tanpa stimulasi oleh cuaca maupun latihan fisik",
        "Ketakutan tanpa alasan yang jelas",
        "Merasa hidup tidak berharga",
        "Sulit untuk beristirahat",
        "Kesulitan dalam menelan",
        "Tidak dapat menikmati hal-hal yang saya lakukan",
        "Perubahan kegiatan jantung dan denyut nadi tanpa stimulasi oleh latihan fisik",
        "Merasa hilang harapan dan putus asa",
        "Mudah marah",
        "Mudah panik",
        "Kesulitan untuk tenang setelah sesuatu yang mengganggu",
        "Takut diri terhambat oleh tugas-tugas yang tidak biasa dilakukan",
        "Sulit untuk antusias pada banyak hal",
        "Sulit mentoleransi gangguan-gangguan terhadap hal yang sedang dilakukan",
        "Berada pada keadaan tegang",
        "Merasa tidak berharga",
        "Tidak dapat memaklumi hal apapun yang menghalangi anda untuk menyelesaikan hal yang sedang Anda lakukan",
        "Merasa ketakutan",
        "Merasa tidak ada harapan untuk masa depan",
        "Merasa tidak berarti",
        "Mudah gelisah",
        "Khawatir dengan situasi saat diri Anda mungkin menjadi panik dan mempermalukan diri sendiri",
        "Gemetar",
        "Sulit untuk meningkatkan inisiatif dalam melakukan sesuatu",
        "Apakah anda sudah yakin dengan seluruh pertanyaan anda"
    )
    private var currentQuestionIndex = 0
    private val answers = mutableMapOf<String, Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKuisionerBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        val answerButtons = listOf(
            binding.answerOption1,
            binding.answerOption2,
            binding.answerOption3,
            binding.answerOption4
        )

        binding.continueButton.isEnabled = false

        for (button in answerButtons) {
            button.setOnClickListener {
                resetButtonColors(answerButtons)

                it.setBackgroundColor(Color.parseColor("#7B6BA8"))
                (it as MaterialButton).setTextColor(Color.WHITE)

                selectedAnswerButton = it as MaterialButton

                if (selectedAnswerButton != null && selectedAnswerButton != it) {
                    selectedAnswerButton?.setBackgroundColor(Color.TRANSPARENT)
                    selectedAnswerButton?.setTextColor(Color.BLACK)
                    selectedAnswerButton?.isChecked = false
                }

                binding.continueButton.isEnabled = true

                it.isChecked = true
            }
        }

        binding.continueButton.setOnClickListener {
            val selectedAnswer = selectedAnswerButton?.text.toString().toIntOrNull() ?: 0
            answers[questions[currentQuestionIndex]] = selectedAnswer

            currentQuestionIndex++
            if (currentQuestionIndex < questions.size) {
                displayQuestion()
                resetButtonColors(answerButtons)
                binding.continueButton.isEnabled = false
            } else {
                val intent = Intent(this, ResultActivity::class.java)
                intent.putExtra(ResultActivity.EXTRA_ANSWERS, answers as HashMap<String, Int>)
                startActivity(intent)
                finish()
            }
        }

        displayQuestion()
        supportActionBar?.hide()
    }

    private fun displayQuestion() {
        binding.questionText.text = questions[currentQuestionIndex]
        binding.answerOption1.text = "0"
        binding.answerOption2.text = "1"
        binding.answerOption3.text = "2"
        binding.answerOption4.text = "3"
    }

    private fun resetButtonColors(buttons: List<MaterialButton>) {
        for (button in buttons) {
            button.setBackgroundColor(Color.TRANSPARENT)
            button.setTextColor(Color.BLACK)
        }
    }
}