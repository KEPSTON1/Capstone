package com.example.capstone.ui.history

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capstone.HistoryViewModelFactory
import com.example.capstone.api.Injection
import com.example.capstone.databinding.ActivityHistoryBinding
import com.example.capstone.pref.UserPreferences

class HistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBinding
    private lateinit var historyAdapter: HistoryAdapter
    private val historyViewModel by viewModels<HistoryViewModel> {
        HistoryViewModelFactory(Injection.provideRepository(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.backButton.setOnClickListener {
            finish()
        }

        val userPreferences = UserPreferences(this)
        val userSession = userPreferences.getSession()
        historyViewModel.setToken(userSession.token) // Simpan token di HistoryViewModel

        setupRecyclerView()
        getHistoryData()
    }

    private fun setupRecyclerView() {
        historyAdapter = HistoryAdapter(historyViewModel) // Inisialisasi dengan HistoryViewModel
        binding.rvHistory.layoutManager = LinearLayoutManager(this)
        binding.rvHistory.adapter = historyAdapter
    }

    private fun getHistoryData() {
        historyViewModel.getHistory() // Panggil getHistory tanpa parameter token

        binding.progressBar.visibility = View.VISIBLE

        historyViewModel.history.observe(this) { historyList ->
            historyAdapter.submitList(historyList)

            binding.progressBar.visibility = View.GONE

            if (historyList.isEmpty()) {
                binding.historyImage.visibility = View.VISIBLE
                binding.rvHistory.visibility = View.GONE
            } else {
                binding.historyImage.visibility = View.GONE
                binding.rvHistory.visibility = View.VISIBLE
            }
        }
    }
}