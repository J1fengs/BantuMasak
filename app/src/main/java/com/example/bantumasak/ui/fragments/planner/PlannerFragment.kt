package com.example.bantumasak.ui.fragments.planner

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.bantumasak.R
import com.example.bantumasak.api.response.MealsItem
import com.example.bantumasak.databinding.FragmentHomeBinding
import com.example.bantumasak.databinding.FragmentPlannerBinding
import com.example.bantumasak.local.room.FavoriteRecipe
import com.example.bantumasak.ui.adapter.RecipesAdapter

class PlannerFragment : Fragment() {
    private var _binding: FragmentPlannerBinding? = null
    private val binding get() = _binding!!
    private val plannerViewModel : PlannerViewModel by viewModels()
    private lateinit var adapter: RecipesAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlannerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
    }

    private fun setRecyclerView() {
        adapter = RecipesAdapter()
        adapter.notifyDataSetChanged()
        binding.apply {
            rvPlanner.layoutManager = GridLayoutManager(context, 2)
            rvPlanner.setHasFixedSize(true)
            rvPlanner.adapter = adapter
        }
        getData()
    }

    private fun getData() {
        plannerViewModel.getFavorite()?.observe(viewLifecycleOwner){
            val recipe = setFavoriteRecipe(it)
            adapter.setList(recipe)
        }
    }

    private fun setFavoriteRecipe(recipe: List<FavoriteRecipe>): ArrayList<MealsItem> {
        val recipeList = ArrayList<MealsItem>()
        for (n in recipe) {
            val newRecipe = MealsItem(strMeal = n.recipeName, strMealThumb = n.avatar)
            recipeList.add(newRecipe)
        }
        return recipeList
    }
}