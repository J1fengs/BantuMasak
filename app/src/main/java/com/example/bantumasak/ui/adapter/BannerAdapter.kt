package com.example.bantumasak.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.RecyclerView
import com.example.bantumasak.R
import com.example.bantumasak.api.response.BantuMasakRecipeResponseItem
import com.example.bantumasak.databinding.ItemHomeBannerBinding

class BannerAdapter : RecyclerView.Adapter<BannerAdapter.UserViewHolder>() {
    private var onItemClicked: OnItemClicked? = null

    fun setOnItemClicked(onItemClicked: OnItemClicked) {
        this.onItemClicked = onItemClicked
    }



    inner class UserViewHolder(val binding: ItemHomeBannerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = ItemHomeBannerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(view)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val image = arrayOf(R.drawable.hellskitchen, R.drawable.torikobanner, R.drawable.kitchennightmare)
        val drawableResId = image[position]
        val drawable = holder.itemView.context.getDrawable(drawableResId)
        holder.binding.bannerImage.setImageDrawable(drawable)
    }

    override fun getItemCount(): Int {
        return 3
    }

    interface OnItemClicked {
        fun onItemClicked(data: BantuMasakRecipeResponseItem)
    }
}