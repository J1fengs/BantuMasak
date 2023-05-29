package com.example.bantumasak.ui.activity.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.bantumasak.local.UserModel
import com.example.bantumasak.local.UserPreference
import kotlinx.coroutines.launch

class LoginViewModel(private val pref: UserPreference) : ViewModel() {
    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }

    fun login() {
        viewModelScope.launch {
            pref.login()
        }
    }
    fun saveUser(user: UserModel) {
        viewModelScope.launch {
            pref.saveUser(user)
        }
    }
}