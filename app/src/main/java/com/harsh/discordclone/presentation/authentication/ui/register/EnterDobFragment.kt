package com.harsh.discordclone.presentation.authentication.ui.register

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.harsh.discordclone.R
import com.harsh.discordclone.databinding.FragmentEnterDobBinding
import com.harsh.discordclone.presentation.authentication.AuthViewModel
import com.harsh.discordclone.util.DatePickerFragment
import com.harsh.discordclone.util.ResultData
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class EnterDobFragment : Fragment() {
    private lateinit var binding: FragmentEnterDobBinding
    private val viewModel by activityViewModels<AuthViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentEnterDobBinding.inflate(inflater)

        val currentCal = viewModel.dob
        binding.dob.text = formatDate(currentCal)
        binding.dob.setTextColor(ContextCompat.getColor(requireContext(), R.color.mithril_silver))

        binding.dob.setOnClickListener {
            val datePicker = DatePickerFragment(viewModel.dob).setOnDateSetListener {
                binding.dob.text = formatDate(it)
                binding.dob.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                viewModel.dob = it
                enableSubmitButton()
            }
            datePicker.show(childFragmentManager, "datePicker")
        }
        binding.createAccountButton.setOnClickListener {
            viewModel.createAccount().observe(viewLifecycleOwner) {
                when (it) {
                    //TODO
                    is ResultData.Failed -> {
                        Log.i(TAG, "${it.message}")
                        Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                    }

                    ResultData.Loading -> {
                        Log.i(TAG, "Loading")
                    }

                    is ResultData.Success -> {
                        findNavController().navigate(R.id.action_enterDobFragment_to_authFragment)
                    }
                }
            }
        }


        binding.upBtn.setOnClickListener {
            findNavController().navigateUp()
        }
        return binding.root
    }

    private fun enableSubmitButton() {
        binding.createAccountButton.alpha = 1.0f
        binding.createAccountButton.isEnabled = true
    }

    private fun formatDate(calendar: Calendar): String? {
        val sdf = SimpleDateFormat("MM/dd/yyyy", Locale.US)
        return sdf.format(calendar.time)
    }

    companion object {
        const val TAG = "EnterDobFragment.kt"
    }

}