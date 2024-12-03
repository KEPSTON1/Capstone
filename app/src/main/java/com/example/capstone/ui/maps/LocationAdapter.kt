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
                binding.ivLocationPhoto.visibility = View.GONE
            }

            binding.tvLocationName.text = location.nama ?: "-"
            binding.tvLocationAddress.text = location.alamat ?: "-"
            binding.tvLocationRating.text = "Rating: ${location.rating ?: "-" }"
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
        holder.bind(location)
    }

    override fun getItemCount(): Int = locations.size

    fun updateData(newLocations: List<LocationResponse>) {
        locations = newLocations
        notifyDataSetChanged()
    }
}