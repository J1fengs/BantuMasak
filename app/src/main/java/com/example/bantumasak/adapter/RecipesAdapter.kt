package com.example.bantumasak.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bantumasak.api.response.MealsItem
import com.example.bantumasak.databinding.ItemRecipesBinding

class RecipesAdapter : RecyclerView.Adapter<RecipesAdapter.UserViewHolder>() {
    private val list = ArrayList<MealsItem>()
    private var onItemClicked: OnItemClicked? = null

    fun setOnItemClicked(onItemClicked: OnItemClicked) {
        this.onItemClicked = onItemClicked
    }

    fun setList(user: ArrayList<MealsItem>) {
        list.clear()
        list.addAll(user)
        notifyDataSetChanged()
    }

    inner class UserViewHolder(val binding: ItemRecipesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(recipe: MealsItem) {
            binding.apply {
                Glide.with(itemView).load(recipe.strMealThumb).centerCrop().into(recipeImg)
                recipeName.text = recipe.strMeal
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
        fun onItemClicked(data: MealsItem)
    }
}