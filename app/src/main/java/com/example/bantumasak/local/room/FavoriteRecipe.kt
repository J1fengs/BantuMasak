package com.example.bantumasak.local.room

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "favorite_recipe")
@Parcelize
data class FavoriteRecipe (
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo("recipeName")
    val recipeName: String,


    @ColumnInfo("img_url")
    val avatar: String
): Parcelable