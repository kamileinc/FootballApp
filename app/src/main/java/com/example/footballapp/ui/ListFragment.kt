package com.example.footballapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.footballapp.RecyclerAdapter
import com.example.footballapp.databinding.FragmentListBinding
import com.example.footballapp.utilities.Resource
import com.example.footballapp.view_model.ListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        val view = binding.root

        setUpAdapter()

        return view
    }

    private fun setUpAdapter() {

        lifecycleScope.launchWhenStarted {
            viewModel.teams.observe(viewLifecycleOwner) { event ->
                when (event) {
                    is Resource.Success -> {
                        binding.progressDialog.isVisible = false
                        binding.errorImage.isVisible = false
                        binding.errorText.isVisible = false

                        val adapter = event.data?.let {
                            RecyclerAdapter(it, RecyclerAdapter.OnClickListener { team ->
                                val action =
                                    ListFragmentDirections.actionListFragmentToDetailFragment(team)
                                findNavController().navigate(action)
                            })
                        }
                        binding.recyclerView.adapter = adapter
                    }
                    is Resource.Failure -> {
                        binding.progressDialog.isVisible = false
                        binding.errorImage.isVisible = true
                        binding.errorText.isVisible = true
                        Toast.makeText(
                            requireContext(),
                            event.message?.asString(context),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    is Resource.Loading -> {
                        binding.progressDialog.isVisible = true
                        binding.errorImage.isVisible = false
                        binding.errorText.isVisible = false
                    }
                    else -> Unit
                }
            }
        }
    }
}
