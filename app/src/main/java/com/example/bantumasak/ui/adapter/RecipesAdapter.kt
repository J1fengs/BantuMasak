package com.example.bantumasak.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bantumasak.api.response.BantuMasakRecipeResponseItem
import com.example.bantumasak.api.response.MealsItem
import com.example.bantumasak.databinding.ItemRecipesBinding

class RecipesAdapter : RecyclerView.Adapter<RecipesAdapter.UserViewHolder>() {
    private val list = ArrayList<BantuMasakRecipeResponseItem>()
    private var onItemClicked: OnItemClicked? = null

    fun setOnItemClicked(onItemClicked: OnItemClicked) {
        this.onItemClicked = onItemClicked
    }

    fun setList(user: ArrayList<BantuMasakRecipeResponseItem>) {
        list.clear()
        list.addAll(user)
        notifyDataSetChanged()
    }

    inner class UserViewHolder(val binding: ItemRecipesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(recipe: BantuMasakRecipeResponseItem) {
            binding.apply {
                Glide.with(itemView).load(recipe.image).centerCrop().into(recipeImg)
                recipeName.text = recipe.title
            }
            binding.root.setOnClickListener {
                onItemClicked?.onItemClicked(recipe)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = ItemRecipesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    interface OnItemClicked {
        fun onItemClicked(data: BantuMasakRecipeResponseItem)
    }
}