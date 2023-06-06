package com.example.bantumasak.ui.fragments.discover

import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.bantumasak.R
import com.example.bantumasak.adapter.RecipesAdapter
import com.example.bantumasak.databinding.FragmentDiscoverBinding
import com.example.bantumasak.databinding.FragmentHomeBinding


class DiscoverFragment : Fragment() {
    private var _binding: FragmentDiscoverBinding? = null
    private val binding get() = _binding!!
    private val discoverViewModel: DiscoverViewModel by viewModels()
    private lateinit var adapter: RecipesAdapter
    private lateinit var searchHandler: Handler
    private lateinit var searchRunnable: Runnable

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDiscoverBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Handle Search Bar
        searchHandler = Handler()
        searchRunnable = Runnable { getRecipesData() }
        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                searchHandler.removeCallbacks(searchRunnable)
                searchHandler.postDelayed(searchRunnable, 1000)
            }
        })

        //setRv
        setRecyclerView()
    }

    private fun setRecyclerView(){
        adapter = RecipesAdapter()
        adapter.notifyDataSetChanged()
        binding.apply {
            discoverRv.layoutManager = GridLayoutManager(context, 2)
            discoverRv.setHasFixedSize(true)
            discoverRv.adapter = adapter
        }
    }

    private fun getRecipesData() {
        var query = binding.searchEditText.text.toString()
        discoverViewModel.getRecipe(query)
        discoverViewModel.getList().observe(viewLifecycleOwner) { list ->
            adapter.setList(list)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}