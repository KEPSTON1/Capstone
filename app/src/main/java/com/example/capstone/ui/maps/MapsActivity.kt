package com.example.capstone.ui.maps

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capstone.MapsViewModelFactory
import com.example.capstone.R
import com.example.capstone.api.Injection
import com.example.capstone.databinding.ActivityMapsBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var adapter: LocationAdapter
    private val viewModel by lazy {
        ViewModelProvider(this, MapsViewModelFactory(Injection.provideRepository(this)))[MapsViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        binding.backButton.setOnClickListener {
            finish()
        }

        supportActionBar?.hide()

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        binding.rvLocations.layoutManager = LinearLayoutManager(this)
        adapter = LocationAdapter(emptyList())
        binding.rvLocations.adapter = adapter

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    val keyword = if (it.equals("bali", ignoreCase = true)) {
                        "mental health di bali"
                    } else {
                        "mental health $it"
                    }
                    viewModel.getNearbyLocations(keyword = keyword)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        viewModel.errorMessage.observe(this) { errorMessage ->
            if (errorMessage != null) {
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
                Log.e("MapsActivity", "Error: $errorMessage") // Tambahkan logging
            }
        }

        viewModel.isLoading.observe(this) { isLoading ->

        }

        viewModel.locations.observe(this) { locations ->
            adapter.updateData(locations)
            adapter.notifyDataSetChanged()

            mMap.clear()
            val builder = LatLngBounds.Builder()
            for (location in locations) {
                val latLng = location.lokasi?.let { LatLng(it.latitude, it.longitude) }

                latLng?.let {
                    mMap.addMarker(
                        MarkerOptions()
                            .position(it)
                            .title(location.nama)
                    )
                    builder.include(it)
                }
            }

            val bounds = builder.build()

            mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100))
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        viewModel.getNearbyLocations()
    }

    private fun getMyLocation() {
        if (ContextCompat.checkSelfPermission(
                this.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(
                this.applicationContext,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mMap.isMyLocationEnabled = true
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    val latLng = LatLng(location.latitude, location.longitude)
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
                } else {
                    Toast.makeText(
                        this,
                        "Location is not found. Try Again",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        } else {
            requestPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            if (permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true &&
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
            ) {
                getMyLocation()
            } else {
                Toast.makeText(
                    this,
                    "Permission request denied",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
}