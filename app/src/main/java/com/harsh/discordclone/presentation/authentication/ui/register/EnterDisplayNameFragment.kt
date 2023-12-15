package com.harsh.discordclone.presentation.authentication.ui.register

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.harsh.discordclone.R
import com.harsh.discordclone.databinding.FragmentEnterDisplayNameBinding
import com.harsh.discordclone.presentation.authentication.AuthViewModel

class EnterDisplayNameFragment : Fragment() {
    private lateinit var binding: FragmentEnterDisplayNameBinding
    private val viewModel by activityViewModels<AuthViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentEnterDisplayNameBinding.inflate(inflater)

        binding.displayNameEditText.addTextChangedListener {
            if (it.isNullOrBlank()) {
                binding.nextButton.isEnabled = false
                binding.nextButton.alpha = 0.5f
            } else {
                binding.nextButton.isEnabled = true
                binding.nextButton.alpha = 1.0f
            }
        }
        binding.nextButton.setOnClickListener {
            viewModel.displayName = binding.displayNameEditText.text!!.toString()
            findNavController().navigate(R.id.action_enterDisplayNameFragment_to_enterCredentialsFragment)
        }
        binding.upBtn.setOnClickListener {
            findNavController().navigateUp()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.displayNameEditText.requestFocus()
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(binding.displayNameEditText,InputMethodManager.SHOW_IMPLICIT)
    }
}