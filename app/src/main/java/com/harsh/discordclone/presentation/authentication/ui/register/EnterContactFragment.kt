package com.harsh.discordclone.presentation.authentication.ui.register

import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.harsh.discordclone.R
import com.harsh.discordclone.databinding.FragmentEnterEmailBinding
import com.harsh.discordclone.presentation.authentication.AuthViewModel

class EnterContactFragment : Fragment() {
    private lateinit var binding: FragmentEnterEmailBinding
    private val viewModel by activityViewModels<AuthViewModel>()
    private val auth by lazy { Firebase.auth }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentEnterEmailBinding.inflate(inflater)
        setUpAnimation()

        viewModel.countryCode.observe(viewLifecycleOwner) { countryCode ->
            Log.i(TAG, "country.observe: $countryCode")
            binding.countryCodeTextView.text = getString(
                R.string.country_code,
                countryCode.code,
                countryCode.dialCode
            )
        }

        binding.contactEditText.addTextChangedListener {
            if (it.isNullOrBlank()) {
                binding.nextButton.isEnabled = false
                binding.nextButton.alpha = 0.5f
            } else {
                binding.nextButton.isEnabled = true
                binding.nextButton.alpha = 1.0f
            }
        }
        binding.countryCodeTextView.setOnClickListener {
            findNavController().navigate(R.id.action_enterEmailFragment_to_countryCodeFragment)
        }
        binding.nextButton.setOnClickListener {
            saveContact()

            //TODO phone number auth
//            if (viewModel.isPhoneAuth) {
//                val callback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//                    override fun onVerificationCompleted(p0: PhoneAuthCredential) {
//                        Log.i(TAG, "onVerificationCompleted: ")
//                    }
//
//                    override fun onVerificationFailed(p0: FirebaseException) {
//                        Log.i(TAG, "onVerificationFailed: $p0")
//                    }
//
//                    override fun onCodeSent(
//                        verificationId: String,
//                        token: PhoneAuthProvider.ForceResendingToken
//                    ) {
//                        super.onCodeSent(verificationId, token)
//                        viewModel.phoneAuthVerificationId = verificationId
//
//                    }
//                }
//                val phoneNumber =
//                    "+${viewModel.countryCode.value!!.dialCode}${viewModel.phoneNumber}"
//                Log.i(TAG, "phoneNumber: $phoneNumber")
//                val options = PhoneAuthOptions.newBuilder(auth)
//                    .setPhoneNumber(phoneNumber)
//                    .setTimeout(60L, TimeUnit.SECONDS)
//                    .setActivity(requireActivity())
//                    .setCallbacks(callback)
//                    .build()
//                PhoneAuthProvider.verifyPhoneNumber(options)
//                findNavController().navigate(R.id.action_enterContactFragment_to_enterOtpFragment)
//
//            }
//            else {
//                findNavController().navigate(R.id.action_enterEmailFragment_to_enterDisplayNameFragment)
//            }
            findNavController().navigate(R.id.action_enterEmailFragment_to_enterDisplayNameFragment)

        }

        binding.upBtn.setOnClickListener {
            findNavController().navigateUp()
        }
        return binding.root
    }

    private fun saveContact() {
        if (viewModel.isPhoneAuth) {
            viewModel.phoneNumber = binding.contactEditText.text!!.toString()
        } else {
            viewModel.email = binding.contactEditText.text!!.toString()
        }
    }

    private fun setUpAnimation() {
        binding.motionLayout.setTransitionDuration(ANIM_DURATION)
        binding.editTextMotionLayout.setTransitionDuration(ANIM_DURATION)

        if (viewModel.isPhoneAuth) {
            switchToPhone()
        } else {
            switchToEmail()
        }

        binding.emailButton.setOnClickListener {
            binding.contactEditText.text?.clear()
            switchToEmail()
        }
        binding.phoneButton.setOnClickListener {
            binding.contactEditText.text?.clear()
            switchToPhone()
        }
    }

    private fun switchToEmail() {
        viewModel.isPhoneAuth = false

        binding.motionLayout.transitionToEnd()
        binding.editTextMotionLayout.transitionToEnd()

        binding.contactEditText.text?.clear()
        binding.contactEditText.hint = getString(R.string.email)
        binding.contactEditText.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS

        binding.phoneButton.setTextColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.mithril_silver
            )
        )
        binding.emailButton.setTextColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.white
            )
        )
    }

    private fun switchToPhone() {
        viewModel.isPhoneAuth = true

        binding.motionLayout.transitionToStart()
        binding.editTextMotionLayout.transitionToStart()

        binding.contactEditText.hint = getString(R.string.phone_number)
        binding.contactEditText.inputType = InputType.TYPE_CLASS_PHONE

        binding.phoneButton.setTextColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.white
            )
        )
        binding.emailButton.setTextColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.mithril_silver
            )
        )
    }

    companion object {
        const val TAG = "EnterEmailFragment.kt"
        const val ANIM_DURATION = 190
    }
}