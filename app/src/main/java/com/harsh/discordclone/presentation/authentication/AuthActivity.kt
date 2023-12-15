package com.harsh.discordclone.presentation.authentication

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.harsh.discordclone.R
import com.harsh.discordclone.databinding.ActivityAuthBinding
import com.harsh.discordclone.model.CountryCode
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding
    private lateinit var navController: NavController
    private val viewModel by viewModels<AuthViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = findNavController(R.id.fragment_container_view)

        viewModel.updateCountryCode(CountryCode("India",91,"IN"))

        //TODO reCaptcha
    }

    companion object{
        const val TAG = "AuthActivity.kt"
    }
}