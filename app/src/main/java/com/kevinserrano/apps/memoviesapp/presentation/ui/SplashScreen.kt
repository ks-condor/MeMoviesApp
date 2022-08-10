package com.kevinserrano.apps.memoviesapp.presentation.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreen : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            delay(timeMillis = 2000)
            startActivity(Intent(this@SplashScreen,MoviesActivity::class.java))
            finish()
        }
    }

}