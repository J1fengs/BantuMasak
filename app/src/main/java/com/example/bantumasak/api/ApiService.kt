package com.example.bantumasak.api

import com.example.bantumasak.api.model.LoginModel
import com.example.bantumasak.api.model.RegisterModel
import com.example.bantumasak.api.response.LoginResponse
import com.example.bantumasak.api.response.RecipesResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @POST("register")
    fun registerUser(
        @Body info:RegisterModel
    ): Call<ResponseBody>

    @POST("login")
    fun loginUser(
        @Body info:LoginModel
    ): Call<LoginResponse>

    @GET("search.php")
    fun getRecipe(
        @Query("s") query: String
    ): Call<RecipesResponse>
}