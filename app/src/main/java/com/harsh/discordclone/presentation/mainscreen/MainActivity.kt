package com.harsh.discordclone.presentation.mainscreen

import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.doOnLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.harsh.discordclone.R
import com.harsh.discordclone.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val navBarHeight by lazy {
        binding.bottomNavView.height
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpBottomNavigationView()

        binding.bottomNavView.setOnItemSelectedListener { selectedItem ->
            when (selectedItem.itemId) {
                ITEM_ID_ONE -> {

                }

                ITEM_ID_TWO -> {

                }

                ITEM_ID_THREE -> {

                }

                ITEM_ID_FOUR -> {

                }
            }
            true
        }
        binding.root.setOnClickListener {
            if (binding.bottomNavView.translationY == 0f)
                ObjectAnimator.ofFloat(
                    binding.bottomNavView,
                    "translationY",
                    navBarHeight.toFloat()
                ).start()
            else
                ObjectAnimator.ofFloat(binding.bottomNavView, "translationY", 0f).start()
        }
    }

    private fun setUpBottomNavigationView() {
        binding.bottomNavView.itemIconTintList = null
        binding.bottomNavView.menu.apply {
            add(
                Menu.NONE,
                ITEM_ID_ONE,
                Menu.NONE,
                "Servers"
            ).setIcon(R.drawable.home_page_icon_selector)

            add(
                Menu.NONE,
                ITEM_ID_TWO,
                Menu.NONE,
                "Messages"
            ).setIcon(R.drawable.messages_icon_selector)

            add(
                Menu.NONE,
                ITEM_ID_THREE,
                Menu.NONE,
                "Notifications"
            ).setIcon(R.drawable.notification_icon_selector)

            add(Menu.NONE, ITEM_ID_FOUR, Menu.NONE, "You")
        }
        binding.bottomNavView.labelVisibilityMode = BottomNavigationView.LABEL_VISIBILITY_LABELED
    }

    companion object {
        private const val TAG = "MainActivity.kt"
        private const val ITEM_ID_ONE = 1
        private const val ITEM_ID_TWO = 2
        private const val ITEM_ID_THREE = 3
        private const val ITEM_ID_FOUR = 4
    }
}