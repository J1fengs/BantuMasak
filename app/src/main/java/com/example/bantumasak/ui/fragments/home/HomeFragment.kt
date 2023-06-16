package com.example.bantumasak.ui.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bantumasak.R
import com.example.bantumasak.api.response.BantuMasakRecipeResponseItem
import com.example.bantumasak.ui.adapter.RecipesAdapter
import com.example.bantumasak.api.response.MealsItem
import com.example.bantumasak.databinding.FragmentHomeBinding
import com.example.bantumasak.ui.adapter.BannerAdapter

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var adapter: RecipesAdapter
    private lateinit var bannerAdapter: BannerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
        setBanner()
        getRecipesData()
    }

    private fun setRecyclerView(){
        adapter = RecipesAdapter()
        adapter.notifyDataSetChanged()
        adapter.setOnItemClicked(object : RecipesAdapter.OnItemClicked{
            override fun onItemClicked(data: MealsItem) {
                val bundle = Bundle().apply {
                    putString("recipeId", data.strMeal)
                    putString("avatar", data.strMealThumb)
                }
                findNavController().navigate(R.id.navigation_detail, bundle)
            }
        })
        binding.apply {
            homeRv.layoutManager = GridLayoutManager(context, 2)
            homeRv.setHasFixedSize(true)
            homeRv.adapter = adapter
        }
    }

    private fun setBanner() {
        bannerAdapter = BannerAdapter()
        bannerAdapter.notifyDataSetChanged()
        binding.apply {
            rvBanner.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            rvBanner.adapter = bannerAdapter
        }
    }

    private fun getRecipesData() {
        homeViewModel.getRecipe("Chicken")
        homeViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
        homeViewModel.getList().observe(viewLifecycleOwner) { list ->
            adapter.setList(list)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.homeProgressBar.visibility= if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}