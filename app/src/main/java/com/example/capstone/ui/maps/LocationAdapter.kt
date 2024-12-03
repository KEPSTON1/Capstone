package com.example.capstone.ui.maps

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.capstone.api.response.LocationResponse
import com.example.capstone.databinding.ItemLocationBinding

class LocationAdapter(private var locations: List<LocationResponse>) :
    RecyclerView.Adapter<LocationAdapter.LocationViewHolder>() {

    class LocationViewHolder(private val binding: ItemLocationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(location: LocationResponse) {
            if (location.fotoUrl != null) {
                Glide.with(itemView.context)
                    .load(location.fotoUrl)
                    .into(binding.ivLocationPhoto)
            } else {
                // Tampilkan placeholder image atau sembunyikan ImageView
                binding.ivLocationPhoto.visibility = View.GONE
            }

            // Penanganan null untuk nama, alamat, dan rating
            binding.tvLocationName.text = location.nama ?: "-" // Menampilkan "-" jika nama null
            binding.tvLocationAddress.text = location.alamat ?: "-" // Menampilkan "-" jika alamat null
            binding.tvLocationRating.text = "Rating: ${location.rating ?: "-" }" // Menampilkan "-" jika rating null
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val binding = ItemLocationBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return LocationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        val location = locations[position]
        holder.bind(location) // Memanggil fungsi bind() untuk mengikat data ke View Binding
    }

    override fun getItemCount(): Int = locations.size

    fun updateData(newLocations: List<LocationResponse>) {
        locations = newLocations
        notifyDataSetChanged() // Memberitahu adapter bahwa data telah berubah
    }
}