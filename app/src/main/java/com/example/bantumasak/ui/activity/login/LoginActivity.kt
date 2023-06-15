package com.example.bantumasak.ui.activity.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.bantumasak.api.ApiLoginConfig
import com.example.bantumasak.api.model.LoginModel
import com.example.bantumasak.api.response.LoginResponse
import com.example.bantumasak.databinding.ActivityLoginBinding
import com.example.bantumasak.local.UserModel
import com.example.bantumasak.local.UserPreference
import com.example.bantumasak.ui.activity.main.MainActivity
import com.example.bantumasak.ui.activity.RegisterActivity
import com.example.bantumasak.ui.ViewModelFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var userModel: UserModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setViewModel()
        setView()
    }

    private fun setView() {
        binding.tvButtonToRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }
        binding.loginLoginBtn.setOnClickListener {
            loginUser()
        }
    }

    private fun setViewModel() {
        loginViewModel = ViewModelProvider(
            this, ViewModelFactory(
                UserPreference
                    .getInstance(dataStore)
            )
        )[LoginViewModel::class.java]

        loginViewModel.getUser().observe(this) {
            this.userModel = it
        }
    }

    private fun loginUser() {
        val username = binding.loginEmail.text.toString()
        val password = binding.loginPassword.text.toString()

        if (password.length > 7) {
            showLoading(true)
            ApiLoginConfig.getApiService().loginUser(LoginModel(username, password))
                .enqueue(object : Callback<LoginResponse> {
                    override fun onResponse(
                        call: Call<LoginResponse>,
                        response: Response<LoginResponse>
                    ) {
                        if (response.isSuccessful) {
                            showLoading(false)
                            loginViewModel.login()
                            loginViewModel.saveUser(UserModel(true))
                            Toast.makeText(
                                this@LoginActivity,
                                "Login Success",
                                Toast.LENGTH_SHORT
                            ).show()
                            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                            finish()
                        } else {
                            showLoading(false)
                            Toast.makeText(
                                this@LoginActivity,
                                "Incorrect Password or Email",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        showLoading(false)
                        Log.d("OnFailure : ", t.message.toString())
                    }
                })
        } else {
            Toast.makeText(
                this@LoginActivity,
                "Password must be at least 8 characters",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.loginProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}