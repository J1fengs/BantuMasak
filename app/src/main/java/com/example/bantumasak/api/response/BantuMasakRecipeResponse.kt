package com.example.bantumasak.api.response

import com.google.gson.annotations.SerializedName
import retrofit2.http.Field

data class BantuMasakRecipeResponse(
	@field:SerializedName("BantuMasakArray")
	val bantuMasakArray: Array<BantuMasakRecipeResponseItem>? = null,

	@field:SerializedName("BantuMasakRecipeResponse")
	val bantuMasakRecipeResponse: ArrayList<BantuMasakRecipeResponseItem>? = null
)

data class BantuMasakRecipeResponseItem(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("ingredient")
	val ingredient: String? = null,

	@field:SerializedName("instruction")
	val instruction: String? = null,

	@field:SerializedName("__v")
	val v: Int? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("title")
	val title: String? = null
)
