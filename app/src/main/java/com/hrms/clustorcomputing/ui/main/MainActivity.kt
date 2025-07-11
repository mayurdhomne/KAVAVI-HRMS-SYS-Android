package com.hrms.clustorcomputing.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.hrms.clustorcomputing.R
import com.hrms.clustorcomputing.databinding.ActivityMainBinding
import com.hrms.clustorcomputing.ui.auth.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private val viewModel: MainViewModel by viewModels()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupUI()
        setupNavigation()
        setupObservers()
    }
    
    private fun setupUI() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setSupportActionBar(binding.toolbar)
        
        // Set status bar color
        window.statusBarColor = resources.getColor(R.color.primary_color, theme)
    }
    
    private fun setupNavigation() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        
        // Setup app bar configuration with top-level destinations
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_dashboard,
                R.id.navigation_attendance,
                R.id.navigation_history,
                R.id.navigation_profile
            )
        )
        
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.bottomNavigation.setupWithNavController(navController)
        
        // Handle navigation events
        navController.addOnDestinationChangedListener { _, destination, _ ->
            supportActionBar?.title = when (destination.id) {
                R.id.navigation_dashboard -> "Dashboard"
                R.id.navigation_attendance -> "Attendance"
                R.id.navigation_history -> "History"
                R.id.navigation_profile -> "Profile"
                else -> "Employee Attendance"
            }
        }
    }
    
    private fun setupObservers() {
        lifecycleScope.launch {
            viewModel.logoutState.collect { shouldLogout ->
                if (shouldLogout) {
                    navigateToLogin()
                }
            }
        }
        
        lifecycleScope.launch {
            viewModel.userProfile.collect { user ->
                // Update UI with user info if needed
                user?.let {
                    supportActionBar?.subtitle = "Welcome, ${it.name}"
                }
            }
        }
    }
    
    override fun onSupportNavigateUp(): Boolean {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
    
    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
    
    override fun onResume() {
        super.onResume()
        viewModel.refreshUserData()
    }
}