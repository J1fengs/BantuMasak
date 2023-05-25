package com.example.bantumasak.ui.main

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.bantumasak.R
import com.example.bantumasak.databinding.ActivityMainBinding
import com.example.bantumasak.local.UserPreference
import com.example.bantumasak.ui.ViewModelFactory
import com.example.bantumasak.ui.WelcomeActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setViewModel()
    }

    private fun setViewModel() {
        mainViewModel = ViewModelProvider(
            this, ViewModelFactory(
                UserPreference
                    .getInstance(dataStore)
            )
        )[MainViewModel::class.java]

        mainViewModel.getUser().observe(this){
            if(!it.isLogin){
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout_menu -> {
                mainViewModel.logout()
                return true
            }

            else -> return true
        }
    }
}