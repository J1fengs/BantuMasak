package com.example.bantumasak.ui.fragments.detail

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bantumasak.api.ApiConfig
import com.example.bantumasak.api.response.MealsItem
import com.example.bantumasak.api.response.RecipesResponse
import com.example.bantumasak.local.room.FavoriteRecipe
import com.example.bantumasak.local.room.FavoriteRecipeDatabase
import com.example.bantumasak.local.room.RecipeDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application) : AndroidViewModel(application) {
    private val _listRecipe = MutableLiveData<ArrayList<MealsItem>>()
    private val listRecipe: LiveData<ArrayList<MealsItem>> = _listRecipe

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    //Room Database
    private var recipeDao: RecipeDao?
    private var favoriteDb: FavoriteRecipeDatabase?

    init {
        favoriteDb = FavoriteRecipeDatabase.getDatabase(application)
        recipeDao = favoriteDb?.RecipeDao()
    }

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

    //Room
    fun addFavorite(recipeName: String, avatar: String){
        CoroutineScope(Dispatchers.IO).launch {
            val favUser = FavoriteRecipe(recipeName,avatar)
            recipeDao?.addFavorite(favUser)
        }
    }
}