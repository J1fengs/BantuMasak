package com.example.bantumasak.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.bantumasak.api.ApiConfig
import com.example.bantumasak.api.model.RegisterModel
import com.example.bantumasak.databinding.ActivityRegisterBinding
import com.example.bantumasak.ui.login.LoginActivity
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setView()
    }

    private fun setView() {
        binding.tvButtonToLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
        binding.registerSignupBtn.setOnClickListener {
            registerUser()
        }
    }

    private fun registerUser() {
        val email = binding.registerEmail.text.toString()
        val password = binding.registerPassword.text.toString()
        val confirmPassword = binding.registerConfirmpassword.text.toString()

        if (password.length > 7) {
            if (password == confirmPassword) {
                showLoading(true)
                ApiConfig.getApiService().registerUser(RegisterModel(email, password))
                    .enqueue(object :
                        Callback<ResponseBody> {
                        override fun onResponse(
                            call: Call<ResponseBody>,
                            response: Response<ResponseBody>
                        ) {
                            if (response.isSuccessful){
                                showLoading(false)
                                Toast.makeText(
                                    this@RegisterActivity,
                                    "Successfully created an account",
                                    Toast.LENGTH_SHORT
                                ).show()
                                finish()
                                startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                            } else {
                                showLoading(false)
                                Toast.makeText(
                                    this@RegisterActivity,
                                    "Email is already taken",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                            showLoading(false)
                            Log.d("OnFailure : ", t.message.toString())
                        }
                    })
            } else {
                Toast.makeText(
                    this@RegisterActivity,
                    "password and confirm password fields do not match",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            Toast.makeText(
                this@RegisterActivity,
                "Password must be at least 8 characters",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.registerProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}