package com.example.capstone.ui.profile

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.capstone.EditProfileViewModelFactory
import com.example.capstone.ProfileViewModelFactory
import com.example.capstone.api.Injection
import com.example.capstone.api.response.EditProfileRequest
import com.example.capstone.databinding.ActivityEditProfileBinding
import com.example.capstone.pref.UserPreferences

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding
    private lateinit var userPreferences: UserPreferences
    private val viewModel by viewModels<EditProfileViewModel> {
        EditProfileViewModelFactory(Injection.provideRepository(this))
    }

    private val viewModel2 by viewModels<ProfileViewModel> {
        ProfileViewModelFactory(Injection.provideRepository(this))
    }

    private val genderOptions = arrayOf("male", "female")
    private lateinit var genderAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userPreferences = UserPreferences(this)
        val userSession = userPreferences.getSession()
        supportActionBar?.hide()

        viewModel2.getProfile(userSession.token)
        viewModel2.profile.observe(this) { user ->
            if (user != null) {
                binding.etFirstName.setText(user.first_name)
                binding.etLastName.setText(user.last_name)
                binding.etPhone.setText(user.phone)
                binding.etAge.setText(user.age.toString())
            }
        }

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                binding.btnSave.isEnabled = isInputValid()
            }
        }
        binding.etFirstName.addTextChangedListener(textWatcher)
        binding.etLastName.addTextChangedListener(textWatcher)
        binding.etPhone.addTextChangedListener(textWatcher)
        binding.etGender.addTextChangedListener(textWatcher)
        binding.etAge.addTextChangedListener(textWatcher)

        binding.btnSave.isEnabled = isInputValid()

        binding.btnSave.setOnClickListener {
            val firstName = binding.etFirstName.text.toString()
            val lastName = binding.etLastName.text.toString()
            val phone = binding.etPhone.text.toString()
            val gender = binding.etGender.text.toString()
            val age = binding.etAge.text.toString().toIntOrNull() ?: 0

            val requestBody = EditProfileRequest(firstName, lastName, phone, gender, age)

            viewModel.updateProfile(userSession.token, requestBody)
        }

        genderAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, genderOptions)
        binding.etGender.setAdapter(genderAdapter)

        viewModel.updateProfileResponse.observe(this) { response ->
            if (response.success) {
                Toast.makeText(this, "Profil berhasil diperbarui", Toast.LENGTH_SHORT).show()
                setResult(Activity.RESULT_OK)
                finish()
            } else {
                Toast.makeText(this, "Gagal memperbarui profil", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.errorMessage.observe(this) { errorMessage ->
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
        }

        viewModel.isLoading.observe(this) { isLoading ->
            // Tampilkan loading indicator jika isLoading true
        }
    }

    private fun isInputValid(): Boolean {
        return binding.etFirstName.text.toString().isNotEmpty() &&
                binding.etLastName.text.toString().isNotEmpty() &&
                binding.etPhone.text.toString().isNotEmpty() &&
                binding.etGender.text.toString().isNotEmpty() &&
                binding.etAge.text.toString().isNotEmpty()
    }
}