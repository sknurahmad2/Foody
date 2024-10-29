package com.example.easyfood.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.easyfood.R
import com.example.easyfood.adapters.CategoryMealAdapter
import com.example.easyfood.databinding.ActivityCategoryMealBinding
import com.example.easyfood.fragment.HomeFragment.Companion.CATEGORY_NAME
import com.example.easyfood.viewModel.CategoryMealsViewModel
import retrofit2.Callback

class CategoryMealActivity : AppCompatActivity() {
    lateinit var binding: ActivityCategoryMealBinding
    lateinit var categoryMealViewModel:CategoryMealsViewModel
    lateinit var categoryMealsAdapter : CategoryMealAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prepareRecyclerView()

        categoryMealViewModel = ViewModelProvider(this)[CategoryMealsViewModel::class.java]

        intent.getStringExtra(CATEGORY_NAME)?.let {
            categoryMealViewModel.getMealsByCategory(it)
        }

        observerCategoryLiveData()
    }

    private fun prepareRecyclerView() {
        categoryMealsAdapter = CategoryMealAdapter()
        binding.recyclerCategoryMeal.apply {
            layoutManager = GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter = categoryMealsAdapter
        }
    }

    private fun observerCategoryLiveData() {
        categoryMealViewModel.observeCategoryMealsLiveData().observe(this,Observer{ mealList ->
            categoryMealsAdapter.setMealList(mealList)
            binding.tvCategoryCount.text = "Total Search Found : "+ mealList.size.toString()
        })
    }
}