package com.harsh.discordclone.presentation.authentication.ui.register

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.harsh.discordclone.R
import com.harsh.discordclone.databinding.FragmentEnterCredentialsBinding
import com.harsh.discordclone.presentation.authentication.AuthViewModel

class EnterCredentialsFragment : Fragment() {
    private lateinit var binding: FragmentEnterCredentialsBinding
    private val viewModel by activityViewModels<AuthViewModel>()
    private var isUsernameDone = false
    private var isPasswordDone = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentEnterCredentialsBinding.inflate(inflater)

        setUpRulesAnimation()

        binding.usernameEditText.doOnTextChanged { text, _, _, _ ->
            checkUsername(text)
        }
        binding.passwordEditText.doOnTextChanged { text, _, _, _ ->
            checkPassword(text)
        }

        binding.nextButton.setOnClickListener {
            viewModel.userName = binding.usernameEditText.text!!.toString()
            viewModel.password = binding.passwordEditText.text!!.toString()
            findNavController().navigate(R.id.action_enterCredentialsFragment_to_enterDobFragment)
        }

        binding.upBtn.setOnClickListener {
            findNavController().navigateUp()
        }
        return binding.root
    }


    private fun checkUsername(username: CharSequence?) {
        if (username.isNullOrBlank()) {
            isUsernameDone = false
            disableNextButton()
            return
        }

        val pattern = "[a-zA-Z0-9_.]+".toRegex()
        val isValid = pattern.matches(username)

        if (isValid) {
            isUsernameDone = true
            if (isPasswordDone) {
                enableNextButton()
            }
        } else {
            isUsernameDone = false
            disableNextButton()
        }

    }

    private fun checkPassword(password: CharSequence?) {
        if (password.isNullOrBlank()) {
            isPasswordDone = false
            disableNextButton()
            return
        }

        val isValid = password.length >= 8

        if (isValid) {
            isPasswordDone = true
            if (isUsernameDone) {
                enableNextButton()
            }
        } else {

            isPasswordDone = false
            disableNextButton()
        }

    }

    private fun disableNextButton() {
        Log.i(TAG, "disableNextButton: ")
        binding.nextButton.alpha = 0.5f
        binding.nextButton.isEnabled = false
    }

    private fun enableNextButton() {
        Log.i(TAG, "enableNextButton: ")
        binding.nextButton.alpha = 1f
        binding.nextButton.isEnabled = true
    }

    private fun setUpRulesAnimation() {
        val spDensity =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 1f, resources.displayMetrics)
        val rulesHeight = (binding.usernameRules.textSize * spDensity).toInt()
        binding.usernameRules.height = 0
        binding.passwordRules.height = 0

        val usernameRulesAnim = getUsernameRulesAnimator(rulesHeight)
        val passwordRulesAnim = getPasswordRulesAnimator(rulesHeight)

        binding.usernameEditText.setOnFocusChangeListener { _, isFocused ->
            if (isFocused) {
                reverseAnimSet(usernameRulesAnim)
            } else {
                usernameRulesAnim.start()
            }
        }
        binding.passwordEditText.setOnFocusChangeListener { _, isFocused ->
            if (isFocused) {
                reverseAnimSet(passwordRulesAnim)
            } else {
                passwordRulesAnim.start()
            }
        }
    }


    private fun getUsernameRulesAnimator(rulesHeight: Int): AnimatorSet {
        val heightAnim =
            ObjectAnimator.ofInt(binding.usernameRules, "height", rulesHeight, 0).apply {
                duration = ANIM_DURATION
            }
        val alphaAnim = ObjectAnimator.ofFloat(binding.usernameRules, "alpha", 1f, 0f).apply {
            duration = ANIM_DURATION
        }
        return AnimatorSet().apply {
            playTogether(heightAnim, alphaAnim)
        }
    }

    private fun getPasswordRulesAnimator(rulesHeight: Int): AnimatorSet {
        val heightAnim =
            ObjectAnimator.ofInt(binding.passwordRules, "height", rulesHeight, 0).apply {
                duration = ANIM_DURATION
            }
        val alphaAnim = ObjectAnimator.ofFloat(binding.passwordRules, "alpha", 1f, 0f).apply {
            duration = ANIM_DURATION
        }
        return AnimatorSet().apply {
            playTogether(heightAnim, alphaAnim)
        }
    }

    private fun reverseAnimSet(anim: AnimatorSet) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            anim.reverse()
        } else {
            anim.childAnimations.forEach {
                (it as ObjectAnimator).reverse()
            }
        }
    }

    companion object {
        const val TAG = "EnterCredentialsFragment.kt"
        const val ANIM_DURATION = 200L
    }

}