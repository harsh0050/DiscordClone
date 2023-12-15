package com.harsh.discordclone.presentation.authentication.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.harsh.discordclone.R
import com.harsh.discordclone.databinding.FragmentAuthBinding
import com.harsh.discordclone.presentation.mainscreen.MainActivity

class AuthFragment : Fragment() {
    private lateinit var binding: FragmentAuthBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentAuthBinding.inflate(inflater)
        binding.registerBtn.setOnClickListener {
            findNavController().navigate(R.id.action_authFragment_to_enterEmailFragment)
        }
        binding.loginBtn.setOnClickListener {
            findNavController().navigate(R.id.action_authFragment_to_loginFragment)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(Firebase.auth.currentUser!=null){
            startActivity(Intent(requireContext(), MainActivity::class.java))
            requireActivity().finish()
        }
    }

    companion object {
        const val TAG = "AuthFragment.kt"
    }

}