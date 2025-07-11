package com.hrms.clustorcomputing.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.hrms.clustorcomputing.databinding.ActivitySplashBinding
import com.hrms.clustorcomputing.ui.auth.LoginActivity
import com.hrms.clustorcomputing.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivitySplashBinding
    private val viewModel: SplashViewModel by viewModels()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        splashScreen.setKeepOnScreenCondition { true }
        
        initializeApp()
    }
    
    private fun initializeApp() {
        lifecycleScope.launch {
            // Show splash screen for minimum 2 seconds
            delay(2000)
            
            // Check if user is logged in
            val isLoggedIn = viewModel.isUserLoggedIn()
            
            val intent = if (isLoggedIn) {
                Intent(this@SplashActivity, MainActivity::class.java)
            } else {
                Intent(this@SplashActivity, LoginActivity::class.java)
            }
            
            startActivity(intent)
            finish()
        }
    }
}