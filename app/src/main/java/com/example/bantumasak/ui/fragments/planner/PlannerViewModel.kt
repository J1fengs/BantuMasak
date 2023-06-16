package com.example.bantumasak.ui.fragments.planner

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bantumasak.api.response.MealsItem
import com.example.bantumasak.local.room.FavoriteRecipe
import com.example.bantumasak.local.room.FavoriteRecipeDatabase
import com.example.bantumasak.local.room.RecipeDao

class PlannerViewModel(application: Application) : AndroidViewModel(application) {
    private val _listRecipe = MutableLiveData<ArrayList<MealsItem>>()
    private val listRecipe: LiveData<ArrayList<MealsItem>> = _listRecipe

    //Room Database
    private var recipeDao: RecipeDao?
    private var favoriteDb: FavoriteRecipeDatabase?

    init {
        favoriteDb = FavoriteRecipeDatabase.getDatabase(application)
        recipeDao = favoriteDb?.RecipeDao()
    }

    fun getFavorite(): LiveData<List<FavoriteRecipe>>?{
        return recipeDao?.getFavoriteRecipe()
    }
}