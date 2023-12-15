package com.harsh.discordclone.presentation.authentication.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.harsh.discordclone.databinding.FragmentEnterOtpBinding
import com.harsh.discordclone.presentation.authentication.AuthViewModel

class EnterOtpFragment : Fragment() {
    private lateinit var binding: FragmentEnterOtpBinding
    private val viewModel by activityViewModels<AuthViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEnterOtpBinding.inflate(inflater)
        binding.otpEditText.doOnTextChanged { text, start, before, count ->
            if(text.isNullOrBlank() || text.length<6){
                disableVerifyButton()
            }else{
                enableVerifyButton()
            }
        }

        binding.verifyButton.setOnClickListener{
            val code = binding.otpEditText.text!!.toString()
            val credentials:
                    PhoneAuthCredential = PhoneAuthProvider.getCredential(viewModel.phoneAuthVerificationId, "code")
//            Log.i(TAG, credentials.smsCode.toString())
//            Firebase.auth.sign

        }

        binding.upBtn.setOnClickListener {
            findNavController().navigateUp()
        }
        return binding.root
    }

    private fun disableVerifyButton(){
        binding.verifyButton.isEnabled = false
        binding.verifyButton.alpha = 0.5f
    }
    private fun enableVerifyButton(){
        binding.verifyButton.isEnabled = true
        binding.verifyButton.alpha = 1.0f
    }

    companion object{
        const val TAG = "EnterOtpFragment.kt"
    }

}