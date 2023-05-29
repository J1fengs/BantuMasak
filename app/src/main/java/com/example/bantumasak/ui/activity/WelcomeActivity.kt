package com.example.bantumasak.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bantumasak.databinding.ActivityWelcomeBinding
import com.example.bantumasak.ui.activity.login.LoginActivity

class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setView()
    }

    private fun setView() {
        binding.welcomeSignup.setOnClickListener{
            startActivity(Intent(this, RegisterActivity::class.java))
        }
        binding.welcomeLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}