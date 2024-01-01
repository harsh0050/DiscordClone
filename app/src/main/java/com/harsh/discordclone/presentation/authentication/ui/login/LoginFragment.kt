package com.harsh.discordclone.presentation.authentication.ui.login

import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.harsh.discordclone.R
import com.harsh.discordclone.databinding.FragmentLoginBinding
import com.harsh.discordclone.presentation.authentication.AuthViewModel
import com.harsh.discordclone.util.ResultData

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val viewModel by activityViewModels<AuthViewModel>()
    private var isIdDone = false
    private var isPasswordDone = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater)
        binding.editTextMotionLayout.setTransitionDuration(ANIM_DURATION)

        viewModel.isPhoneLogin.observe(viewLifecycleOwner) { isPhoneLogin ->
            if (isPhoneLogin) {
                switchToPhone()
            } else {
                switchToEmail()
            }
        }

        binding.contactEditText.addTextChangedListener { text ->
            if (text.isNullOrBlank()) {
                isIdDone = false
                disableLoginButton()

            } else {
                isIdDone = true
                if (isPasswordDone) {
                    enableLoginButton()
                }
            }
            viewModel.updateIsPhoneLogin(isPhoneNumber(text))
        }
        binding.passwordEditText.addTextChangedListener { text ->
            if (text.isNullOrBlank()) {
                isPasswordDone = false
                disableLoginButton()
                return@addTextChangedListener
            }
            isPasswordDone = true
            if (isIdDone) {
                enableLoginButton()
            }
        }

        binding.loginBtn.setOnClickListener {
            viewModel.login(
                binding.contactEditText.text!!.toString(),
                binding.passwordEditText.text!!.toString()
            ).observe(viewLifecycleOwner) {
                when (it) {
                    is ResultData.Failed -> {
                        binding.errorMsg.text = it.message
                    }

                    ResultData.Loading -> Log.i(TAG, "Loading")

                    is ResultData.Success -> {
                        findNavController().navigateUp()
                    }
                }
            }
        }

        viewModel.countryCode.observe(viewLifecycleOwner) { countryCode ->
            binding.countryCodeTextView.text = getString(
                R.string.country_code,
                countryCode.code,
                countryCode.dialCode
            )
        }

        binding.countryCodeTextView.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_countryCodeFragment)
        }

        binding.upBtn.setOnClickListener {
            findNavController().navigateUp()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        switchToEmail()
    }

    private fun switchToEmail() {
        binding.editTextMotionLayout.transitionToEnd()
    }

    private fun switchToPhone() {
        binding.editTextMotionLayout.transitionToStart()
    }

    private fun enableLoginButton() {
        binding.loginBtn.isEnabled = true
        binding.loginBtn.alpha = 1.0f
    }

    private fun disableLoginButton() {
        binding.loginBtn.isEnabled = false
        binding.loginBtn.alpha = 0.5f
    }

    private fun isPhoneNumber(userId: Editable?): Boolean {
        if (userId.isNullOrBlank()) {
            return false
        }

        for (i in (userId.length - 1) downTo 0) {
            if (!userId[i].isDigit())
                return false
        }
        return true
    }

    companion object {
        const val TAG = "LoginFragment.kt"
        const val ANIM_DURATION = 190
    }
}