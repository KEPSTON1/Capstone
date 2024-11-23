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
            val intent = Intent(this, ResultActivity::class.java)
            startActivity(intent)
        }
        supportActionBar?.hide()
    }

    private fun resetButtonColors(buttons: List<MaterialButton>) {
        for (button in buttons) {
            button.setBackgroundColor(Color.TRANSPARENT)
            button.setTextColor(Color.BLACK)
        }
    }
}