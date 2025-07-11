package com.hrms.clustorcomputing.ui.auth

import android.graphics.Color
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.hrms.clustorcomputing.R

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        window.statusBarColor = resources.getColor(R.color.colorAccent, theme)
        window.navigationBarColor = resources.getColor(R.color.colorAccent, theme)
    }
}
