package com.realityexpander.viewlifecycleownerdemo.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.realityexpander.viewlifecycleownerdemo.R
import com.realityexpander.viewlifecycleownerdemo.databinding.FragmentFirstBinding

class FirstFragment : Fragment() {

//    companion object {
//        fun newInstance() = FirstFragment()
//    }

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    // delegates to the viewModel scoped to the activity
    private val sharedViewModel: SharedViewModel by activityViewModels()

    // If using regular `by viewModel`, a new instance is created (DONT DO THIS)
    //private val sharedViewModel: SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)

        // Show country name
        sharedViewModel.country.observe(viewLifecycleOwner) { country ->
            binding.countryEt.setText(country)
        }

        // Set country name
        binding.button.setOnClickListener {
            sharedViewModel.saveCountry(binding.countryEt.text.toString())
            findNavController().navigate(R.id.action_firstFragment_to_secondFragment)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}