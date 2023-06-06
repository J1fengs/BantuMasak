package com.example.bantumasak.ui.activity.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.bantumasak.local.UserModel
import com.example.bantumasak.local.UserPreference
import kotlinx.coroutines.launch

class MainViewModel(private val pref: UserPreference): ViewModel() {
    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            pref.logout()
        }
    }
}