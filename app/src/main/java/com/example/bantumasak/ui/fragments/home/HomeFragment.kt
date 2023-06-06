package com.example.bantumasak.ui.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.bantumasak.R
import com.example.bantumasak.adapter.RecipesAdapter
import com.example.bantumasak.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var adapter: RecipesAdapter
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

        homeViewModel.getRecipe("Chicken")
        homeViewModel.getList().observe(viewLifecycleOwner) { list ->
            adapter.setList(list)
        }
    }

    private fun setRecyclerView(){
        adapter = RecipesAdapter()
        adapter.notifyDataSetChanged()
        binding.apply {
            homeRv.layoutManager = GridLayoutManager(context, 2)
            homeRv.setHasFixedSize(true)
            homeRv.adapter = adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}