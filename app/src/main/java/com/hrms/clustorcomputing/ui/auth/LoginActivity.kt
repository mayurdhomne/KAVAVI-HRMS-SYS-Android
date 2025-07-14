package com.hrms.clustorcomputing.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.hrms.clustorcomputing.R
import com.hrms.clustorcomputing.databinding.ActivityLoginBinding
import com.hrms.clustorcomputing.ui.main.MainActivity
import com.hrms.clustorcomputing.utils.BiometricManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityLoginBinding
    private val viewModel: AuthViewModel by viewModels()
    
    @Inject
    lateinit var biometricManager: BiometricManager
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupUI()
        setupObservers()
        setupClickListeners()
        checkBiometricAvailability()
    }

    private fun setupUI() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = resources.getColor(R.color.primary_color, theme)
    }
    
    private fun setupObservers() {
        lifecycleScope.launch {
            viewModel.loginState.collect { state ->
                when (state) {
                    is AuthViewModel.LoginState.Loading -> {
                        showLoading(true)
                    }
                    is AuthViewModel.LoginState.Success -> {
                        showLoading(false)
                        navigateToMain()
                    }
                    is AuthViewModel.LoginState.Error -> {
                        showLoading(false)
                        showError(state.message)
                    }
                    is AuthViewModel.LoginState.Idle -> {
                        showLoading(false)
                    }
                }
            }
        }
    }
    
    private fun setupClickListeners() {
        binding.loginButton.setOnClickListener {
            performLogin()
        }
        
        binding.biometricLoginButton.setOnClickListener {
            performBiometricLogin()
        }
        
        binding.forgotPasswordText.setOnClickListener {
            // Handle forgot password
            Toast.makeText(this, getString(R.string.forgot_password_coming_soon), Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun checkBiometricAvailability() {
        val biometricStatus = biometricManager.isBiometricAvailable(this)
        binding.biometricLoginButton.visibility = if (biometricStatus == BiometricManager.BiometricStatus.SUCCESS) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }
    
    private fun performLogin() {
        val email = binding.emailEditText.text.toString().trim()
        val password = binding.passwordEditText.text.toString().trim()
        
        // Basic validation
        if (email.isEmpty()) {
            binding.emailInputLayout.error = getString(R.string.email_required)
            return
        }
        
        if (password.isEmpty()) {
            binding.passwordInputLayout.error = getString(R.string.password_required)
            return
        }
        
        // Clear previous errors
        binding.emailInputLayout.error = null
        binding.passwordInputLayout.error = null
        
        // Perform login
        viewModel.login(email, password)
    }
    
    private fun performBiometricLogin() {
        lifecycleScope.launch {
            val result = biometricManager.authenticateWithBiometric(
                this@LoginActivity,
                getString(R.string.biometric_login),
                getString(R.string.biometric_login_subtitle),
                getString(R.string.biometric_login_description)
            )
            
            when (result.status) {
                BiometricManager.BiometricStatus.SUCCESS -> {
                    viewModel.biometricLogin()
                }
                else -> {
                    showError(getString(R.string.biometric_authentication_failed, result.message))
                }
            }
        }
    }
    
    private fun showLoading(show: Boolean) {
        binding.progressBar.visibility = if (show) View.VISIBLE else View.GONE
        binding.loginButton.isEnabled = !show
        binding.biometricLoginButton.isEnabled = !show
    }
    
    private fun showError(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }
    
    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}
