package com.example.bantumasak.ui.activity.main

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.bantumasak.R
import com.example.bantumasak.databinding.ActivityMainBinding
import com.example.bantumasak.local.UserPreference
import com.example.bantumasak.ui.ViewModelFactory
import com.example.bantumasak.ui.activity.WelcomeActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.navView.background = null

        setBottomNav()
        setViewModel()
    }

    private fun setViewModel() {
        mainViewModel = ViewModelProvider(
            this, ViewModelFactory(
                UserPreference
                    .getInstance(dataStore)
            )
        )[MainViewModel::class.java]

//        mainViewModel.getUser().observe(this){
//            if(!it.isLogin){
//                startActivity(Intent(this, WelcomeActivity::class.java))
//                finish()
//            }
//        }
    }

    private fun setBottomNav() {
        //Bottom Navigation
        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_discover, R.id.navigation_camera, R.id.navigation_planner, R.id.navigation_profile
            )
        )
        supportActionBar?.hide()
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    fun hideBottomNav() {
        binding.navView.visibility = View.GONE
        binding.bottomBar.visibility = View.GONE
    }

    fun showBottomNav() {
        binding.bottomBar.visibility = View.VISIBLE
        binding.navView.visibility = View.VISIBLE

    }

}