package com.realityexpander.viewlifecycleownerdemo.ui.main

import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.realityexpander.viewlifecycleownerdemo.R
import com.realityexpander.viewlifecycleownerdemo.databinding.FragmentFirstBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

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

    var count1 = 0
    var count2 = 0

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

        binding.button2.setOnClickListener {
            findNavController().navigate(R.id.action_firstFragment_to_secondFragment)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Emit flow 1
        val flow1 = flow {
            delay(4000)
            count1++
            emit("$count1")
        }

        // Emit flow 2
        val flow2 = flow {
            delay(5000)
            count2++
            emit("$count2")
        }

        // This will be collected twice, because its not cancelled when navigating away.
        // The flow1 stays active as its connected to the fragment's lifecycle (ie: this)
        this.lifecycleScope.launchWhenStarted {
            flow1.collect {
//                Snackbar.make(binding.root, "flow 1, count1=$it", Snackbar.LENGTH_SHORT)
//                    .show()
                Toast.makeText(requireContext(), "flow 1, count1=$it", Toast.LENGTH_SHORT)
                    .show()

                println("flow 1, count1=$it")
            }
        }

        // This will be collected only once, because flow2 is cancelled when the view is destroyed
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            flow2.collect {
                Snackbar.make(binding.root, "flow 2, count2=$it", Snackbar.LENGTH_SHORT)
                    .setAnchorView(binding.message)
                    .show()

                println("flow 2, count2=$it")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}