package com.example.bantumasak.ui.fragments.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.bantumasak.databinding.FragmentDetailBinding
import com.example.bantumasak.ui.activity.main.MainActivity
import com.example.bantumasak.ui.fragments.detail.DetailFragmentArgs

class DetailFragment : Fragment() {
    private val detailViewModel: DetailViewModel by viewModels()
    private val args: DetailFragmentArgs by navArgs()
    private lateinit var recipeName: String
    private lateinit var recipeImg: String
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        recipeName = args.recipeId
        recipeImg = args.avatar
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setView()
        detailViewModel.getRecipe(recipeName)
        detailViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
        detailViewModel.getList().observe(viewLifecycleOwner) {recipe ->
            binding.apply {
                Glide.with(requireContext()).load(recipe[0].strMealThumb).centerCrop().into(detailRecipeImage)
                detailRecipename.text = recipe[0].strMeal
                detailRecipedescription.text = recipe[0].strInstructions
            }
        }
    }

    private fun setView() {
        binding.detailAddToPlan.setOnClickListener {
            detailViewModel.addFavorite(recipeName, recipeImg)
            Toast.makeText(context, "Add To Plan Success", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.detailProgressBar.visibility= if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).hideBottomNav()
    }

    override fun onPause() {
        super.onPause()
        (activity as MainActivity).showBottomNav()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}