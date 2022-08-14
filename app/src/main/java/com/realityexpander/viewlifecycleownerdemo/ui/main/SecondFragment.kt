package com.realityexpander.viewlifecycleownerdemo.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.realityexpander.sharedviewmodelfragment.R
import com.realityexpander.sharedviewmodelfragment.databinding.FragmentSecondBinding

/**
 * A simple [Fragment] subclass.
 * Use the [SecondFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!

    // delegates to the viewModel scoped to the activity
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)

        // Show country name
        sharedViewModel.country.observe(viewLifecycleOwner) { country ->
            binding.countryEt.setText(country)
        }

        // Set country name
        binding.button.setOnClickListener {
            sharedViewModel.saveCountry(binding.countryEt.text.toString())
            findNavController().navigate(R.id.action_secondFragment_to_firstFragment)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}