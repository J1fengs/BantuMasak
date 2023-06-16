package com.example.bantumasak.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavoriteRecipe::class], version = 1)
abstract class FavoriteRecipeDatabase : RoomDatabase(){
    abstract fun RecipeDao(): RecipeDao

    companion object {
        @Volatile
        private var INSTANCE: FavoriteRecipeDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): FavoriteRecipeDatabase{
            if (INSTANCE == null){
                synchronized(FavoriteRecipeDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        FavoriteRecipeDatabase::class.java, "favorite_recipe_database").build()
                }
            }
            return INSTANCE as FavoriteRecipeDatabase
        }
    }
}