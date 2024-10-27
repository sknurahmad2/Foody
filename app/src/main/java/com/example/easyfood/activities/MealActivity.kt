package com.example.easyfood.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.easyfood.R
import com.example.easyfood.databinding.ActivityMealBinding
import com.example.easyfood.fragment.HomeFragment.Companion.CATEGORY_NAME
import com.example.easyfood.fragment.HomeFragment.Companion.MEAL_AREA
import com.example.easyfood.fragment.HomeFragment.Companion.MEAL_ID
import com.example.easyfood.fragment.HomeFragment.Companion.MEAL_INS
import com.example.easyfood.fragment.HomeFragment.Companion.MEAL_LINK
import com.example.easyfood.fragment.HomeFragment.Companion.MEAL_NAME
import com.example.easyfood.fragment.HomeFragment.Companion.MEAL_STR
import com.example.easyfood.fragment.HomeFragment.Companion.MEAL_THUMB
import com.example.easyfood.pojo.Meal
import com.example.easyfood.viewModel.MealViewModel

class MealActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMealBinding
    private lateinit var mealMvvm : MealViewModel

    private lateinit var mealId : String
    private lateinit var mealName : String
    private lateinit var mealThumb : String
    private lateinit var youtubeLink : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mealMvvm = ViewModelProvider(this)[MealViewModel::class.java]

        getMealInfoFromIntent()

        setUpViewWithMealInformation()

        loadingCase()
        mealMvvm.getMealDetail(mealId)
        observeMealLiveData()

        onYoutubeImageClick()
    }

    private fun onYoutubeImageClick() {
        binding.imgYoutube.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
            startActivity(intent)
        }
    }

    private fun observeMealLiveData() {
        mealMvvm.observeMealDetalisLiveData().observe(this,object :Observer<Meal>{
            override fun onChanged(value: Meal) {
                onResponseCase()
                val meal = value

                binding.tvCategoryInfo.text = "Category : ${meal.strCategory}"
                binding.tvAreaInfo.text = "Area : ${meal.strArea}"
                binding.tvContent.text = meal.strInstructions

                youtubeLink = meal.strYoutube
            }
        })
    }

    private fun setUpViewWithMealInformation() {
        Glide.with(applicationContext)
            .load(mealThumb)
            .into(binding.imgMealDetail)

        binding.collapsingToolbar.title = mealName
        binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.collapsingToolbar.setExpandedTitleColor(resources.getColor(R.color.white))
    }

    private fun getMealInfoFromIntent() {
        val tempIntent = intent

        mealId = tempIntent.getStringExtra(MEAL_ID) ?: "Null"
        mealName = tempIntent.getStringExtra(MEAL_NAME).toString()
        mealThumb = tempIntent.getStringExtra(MEAL_THUMB).toString()
    }

    private fun loadingCase(){
        binding.progressBar.visibility = View.VISIBLE
        binding.btnSave.visibility = View.INVISIBLE
        binding.tvInstructions.visibility = View.INVISIBLE
        binding.tvCategoryInfo.visibility = View.INVISIBLE
        binding.tvAreaInfo.visibility = View.INVISIBLE
        binding.imgYoutube.visibility = View.INVISIBLE
    }

    private fun onResponseCase(){
        binding.progressBar.visibility = View.INVISIBLE
        binding.btnSave.visibility = View.VISIBLE
        binding.tvInstructions.visibility = View.VISIBLE
        binding.tvCategoryInfo.visibility = View.VISIBLE
        binding.tvAreaInfo.visibility = View.VISIBLE
        binding.imgYoutube.visibility = View.VISIBLE
    }

}