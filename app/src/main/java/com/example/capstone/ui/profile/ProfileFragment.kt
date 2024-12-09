package com.example.capstone.ui.profile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.capstone.ProfileViewModelFactory
import com.example.capstone.api.Injection
import com.example.capstone.databinding.FragmentProfileBinding
import com.example.capstone.pref.UserPreferences
import com.example.capstone.ui.welcome.WelcomeActivity

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var userPreferences: UserPreferences
    private val viewModel by viewModels<ProfileViewModel> {
        ProfileViewModelFactory(Injection.provideRepository(requireContext()))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        userPreferences = UserPreferences(requireContext())

        binding.logoutButton.setOnClickListener {
            userPreferences.logout()

            val intent = Intent(requireContext(), WelcomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            activity?.finish()
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userPreferences = UserPreferences(requireContext())
        val userSession = userPreferences.getSession()

        viewModel.getProfile(userSession.token)

        viewModel.profile.observe(viewLifecycleOwner) { user ->
            if (user != null) {
                binding.tvName.text = user.first_name + " " + user.last_name
                binding.tvEmail.text = user.email
                binding.tvPhone.text = user.phone
                binding.tvGender.text = user.gender
                binding.tvAge.text = user.age.toString()
            }
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            if (errorMessage != null) {
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->

        }

        binding.editProfileButton.setOnClickListener {
            val intent = Intent(requireActivity(), EditProfileActivity::class.java)
            startActivityForResult(intent, EDIT_PROFILE_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == EDIT_PROFILE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // Refresh data profile di fragment
            val userPreferences = UserPreferences(requireContext())
            val userSession = userPreferences.getSession()
            viewModel.getProfile(userSession.token)
        }
    }

    companion object {
        private const val EDIT_PROFILE_REQUEST_CODE = 1001
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}