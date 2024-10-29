package com.example.easyfood.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.easyfood.activities.MealActivity
import com.example.easyfood.adapters.CategoryAdapter
import com.example.easyfood.adapters.MostPopularAdapter
import com.example.easyfood.databinding.FragmentHomeBinding
import com.example.easyfood.pojo.MealsByCategory
import com.example.easyfood.pojo.Meal
import com.example.easyfood.viewModel.HomeViewModel


class HomeFragment : Fragment() {
    private lateinit var binding : FragmentHomeBinding
    private lateinit var homeMvvm: HomeViewModel
    private lateinit var randomMeal : Meal
    private lateinit var popularItemsAdapter: MostPopularAdapter
    private lateinit var categoriesAdapter : CategoryAdapter


    companion object{
        const val MEAL_ID="com.example.easyfood.fragment.idMeal"
        const val MEAL_NAME="com.example.easyfood.fragment.nameMeal"
        const val MEAL_THUMB="com.example.easyfood.fragment.thumbMeal"
        const val CATEGORY_NAME="com.example.easyfood.fragment.categoryName"
        const val MEAL_STR="com.example.easyfood.fragment.strMeal"
        const val MEAL_AREA="com.example.easyfood.fragment.strArea"
        const val MEAL_INS="com.example.easyfood.fragment.instruction"
        const val MEAL_LINK="com.example.easyfood.fragment.youtube"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeMvvm = ViewModelProvider(this)[HomeViewModel::class.java]
        popularItemsAdapter = MostPopularAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preparePopularItemsRecyclerView()

        homeMvvm.getRandomMeal()
        observerRandomMeal()
        onRandomMealClick()

        homeMvvm.getPopularItems()
        observePopularItemLiveData()

        onPopularItemClick()

        prepareCategoriesRecyclerView()
        homeMvvm.getCategoies()
        observeCategoriesLiveData()
    }

    private fun prepareCategoriesRecyclerView() {
        categoriesAdapter = CategoryAdapter()
        binding.recViewCategories.apply {
            layoutManager = GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
            adapter = categoriesAdapter
        }
    }

    private fun observeCategoriesLiveData() {
        homeMvvm.observeCategoryLiveData().observe(viewLifecycleOwner, Observer { categories ->
            categoriesAdapter.setCategoriesList(categories)
        })
    }

    private fun onPopularItemClick() {
        popularItemsAdapter.onItemClick = { meals ->
            val intent = Intent(activity,MealActivity::class.java)
            intent.putExtra(MEAL_ID,meals.idMeal)
            intent.putExtra(MEAL_NAME,meals.strMeal)
            intent.putExtra(MEAL_THUMB,meals.strMealThumb)
            startActivity(intent)
        }
    }

    private fun preparePopularItemsRecyclerView() {
        binding.recViewMealsPopular.apply {
            layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
            adapter = popularItemsAdapter
        }
    }

    private fun observePopularItemLiveData() {
        homeMvvm.observePopularItemsLiveData().observe(viewLifecycleOwner) { mealList ->
            popularItemsAdapter.setMeals(mealsList = mealList as ArrayList<MealsByCategory>)
        }
    }

    private fun onRandomMealClick() {
        binding.randomMealCard.setOnClickListener{
            val intent = Intent(activity,MealActivity::class.java)
            intent.putExtra(MEAL_ID,randomMeal.idMeal)
            intent.putExtra(MEAL_NAME,randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB,randomMeal.strMealThumb)
            intent.putExtra(MEAL_AREA,randomMeal.strArea)
            intent.putExtra(CATEGORY_NAME,randomMeal.strCategory)
            intent.putExtra(MEAL_INS,randomMeal.strInstructions)
            intent.putExtra(MEAL_LINK,randomMeal.strYoutube)
            startActivity(intent)
        }
    }

    private fun observerRandomMeal() {
        homeMvvm.observeRandomMealLiveData().observe(viewLifecycleOwner) { meal ->
            Glide.with(this@HomeFragment)
                .load(meal.strMealThumb)
                .into(binding.imgRandomMeal)

            randomMeal = meal // Update randomMeal when data is received
            binding.randomMealCard.isEnabled = true // Enable the card
        }
        binding.randomMealCard.isEnabled = false // Disable the card initially
    }
}