package com.example.bantumasak.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RecipeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(favoriteRecipe: FavoriteRecipe)

    @Query("SELECT * FROM favorite_recipe")
    fun getFavoriteRecipe(): LiveData<List<FavoriteRecipe>>
}