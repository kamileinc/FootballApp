package com.example.footballapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.footballapp.R
import com.example.footballapp.databinding.FragmentDetailBinding
import com.example.footballapp.view_model.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        val view = binding.root

        setUpDetails()

        binding.favoritesButton.setOnClickListener {
            viewModel.changeFavoriteStatus()
        }

        return view
    }

    private fun setUpDetails() {
        lifecycleScope.launchWhenStarted {
            viewModel.selectedTeam.observe(viewLifecycleOwner) { team ->
                binding.name.text = team.name
                binding.coach.text = team.coach
                binding.stadium.text = team.stadium
                loadPhoto(team.emblem)
                loadFavouriteImage(team.favorite)
            }
        }
    }

    private fun loadPhoto(emblem: String) {
        Glide.with(requireContext())
            .load(emblem)
            .apply(
                RequestOptions.placeholderOf(R.drawable.ic_baseline_image_24)
                    .error(R.drawable.ic_baseline_image_not_supported_24)
            )
            .into(binding.emblem)
    }

    private fun loadFavouriteImage(favorite: Boolean?) {
        if (favorite != null && favorite == true) {
            binding.favoritesButton.setImageResource(R.drawable.ic_baseline_star_24)
        } else {
            binding.favoritesButton.setImageResource(R.drawable.ic_baseline_star_outline_24)
        }
    }
}
