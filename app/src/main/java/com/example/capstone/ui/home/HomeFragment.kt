package com.example.capstone.ui.home

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
import com.example.capstone.databinding.FragmentHomeBinding
import com.example.capstone.pref.UserPreferences
import com.example.capstone.ui.history.HistoryActivity
import com.example.capstone.ui.kuisioner.KuisionerActivity
import com.example.capstone.ui.maps.MapsActivity
import com.example.capstone.ui.profile.ProfileViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
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
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.kuisionerCard.setOnClickListener {
            val intent = Intent(requireContext(), KuisionerActivity::class.java)
            startActivity(intent)
        }
        binding.historyCard.setOnClickListener {
            val intent = Intent(requireContext(), HistoryActivity::class.java)
            startActivity(intent)
        }
        binding.mapsCard.setOnClickListener {
            val intent = Intent(requireContext(), MapsActivity::class.java)
            startActivity(intent)
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
                binding.usernameText.text = "\uD83D\uDC4B\uD83C\uDFFB Hi " + user.first_name
            }
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            if (errorMessage != null) {
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}