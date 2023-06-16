package com.example.bantumasak.ui.fragments.discover

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bantumasak.api.ApiConfig
import com.example.bantumasak.api.response.MealsItem
import com.example.bantumasak.api.response.RecipesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DiscoverViewModel : ViewModel() {
    private val _listRecipe = MutableLiveData<ArrayList<MealsItem>>()
    private val listRecipe: LiveData<ArrayList<MealsItem>> = _listRecipe

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    fun getRecipe(query: String){
        _isLoading.value = true
        ApiConfig.getApiService().getRecipe(query).enqueue(object : Callback<RecipesResponse> {
            override fun onResponse(
                call: Call<RecipesResponse>,
                response: Response<RecipesResponse>
            ) {
                if(response.isSuccessful){
                    _isLoading.value = false
                    _listRecipe.postValue(response.body()?.meals!!)
                    Log.d("OnSuccessful : ", response.body().toString())
                } else {
                    _isLoading.value = false
                    Log.d("OnFailure : ", response.body().toString())
                }
            }

            override fun onFailure(call: Call<RecipesResponse>, t: Throwable) {
                Log.d("OnFailure : ", t.message.toString())
            }
        })
    }

    fun getList(): LiveData<ArrayList<MealsItem>> {
        return listRecipe
    }
}