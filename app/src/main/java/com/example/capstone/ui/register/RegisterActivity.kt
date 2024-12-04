package com.example.capstone.ui.register

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.capstone.MainActivity
import com.example.capstone.RegisterViewModelFactory
import com.example.capstone.api.Injection
import com.example.capstone.databinding.ActivityRegisterBinding
import com.example.capstone.ui.login.LoginActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val viewModel by viewModels<RegisterViewModel> {
        RegisterViewModelFactory(Injection.provideRepository(this))
    }

    private val genderOptions = arrayOf("male", "female")
    private lateinit var genderAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginText.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        genderAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, genderOptions)
        binding.edRegisterGender.setAdapter(genderAdapter)

        supportActionBar?.hide()
        setupAction()
    }
    private fun setupAction() {
        binding.signupButton.setOnClickListener {
            val email = binding.edRegisterEmail.text.toString()
            val firstName = binding.edRegisterFirstName.text.toString()
            val lastName = binding.edRegisterLastName.text.toString()
            val password = binding.edRegisterPassword.text.toString()
            val phone = binding.edRegisterPhone.text.toString()
            val gender = binding.edRegisterGender.text.toString()
            val age = binding.edRegisterAge.text.toString().toIntOrNull() ?: 0

            viewModel.register(email, firstName, lastName, password, phone, gender, age)
        }

        viewModel.registerResult.observe(this) { isSuccess ->
            if (isSuccess) {
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            }
        }

        viewModel.errorMessage.observe(this) { errorMessage ->
            if (errorMessage != null) {
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.isLoading.observe(this) { isLoading ->
            binding.signupButton.isEnabled = !isLoading
            if (isLoading) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }
    }
}