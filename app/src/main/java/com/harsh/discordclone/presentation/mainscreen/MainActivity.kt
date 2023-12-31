package com.harsh.discordclone.presentation.mainscreen

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import com.harsh.discordclone.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        binding.root.setOnClickListener {
//            Firebase.auth.signOut()
//        }
        binding.mainContainer.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN->{
                    println("child : action_down")
                }
                MotionEvent.ACTION_MOVE->{
                    println("child : action_move")
                }
            }
            true
        }
    }
}