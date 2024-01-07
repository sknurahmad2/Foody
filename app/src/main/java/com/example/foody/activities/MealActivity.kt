package com.example.foody.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.foody.R
import com.example.foody.databinding.ActivityMealBinding
import com.example.foody.fragments.HomeFragment
import com.example.foody.pojo.Meal
import com.example.foody.viewModel.MealViewModel

class MealActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMealBinding
    private lateinit var mealId:String
    private lateinit var mealName:String
    private lateinit var mealImg:String
    private lateinit var youTubeLink:String
    private lateinit var mealMvvm:MealViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mealMvvm = ViewModelProviders.of(this)[MealViewModel::class.java]
        getMealInformationFromIntent()

        setInformationFromIntent()

        loadingCase()
        mealMvvm.getMealDeatils(mealId)

        observeMealDeatilsLiveData()

        onYouTubeImageClick()
    }

    private fun onYouTubeImageClick() {
        binding.imgYouTube.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youTubeLink))
            startActivity(intent)
        }
    }

    private fun observeMealDeatilsLiveData() {
        mealMvvm.observerMealDeatilListLiveData().observe(this,object :Observer<Meal>{
            override fun onChanged(value: Meal) {
                responceCase()
                val meal = value

                binding.tvMealDetailCategory.text = "Category : ${meal!!.strCategory}"
                binding.tvMealDetailArea.text = "Area : ${meal!!.strArea}"
                binding.tvInstructionDetails.text = meal.strInstructions

                youTubeLink = meal.strYoutube
            }
        })
    }

    private fun setInformationFromIntent() {
        binding.collapsingToolbar.title = mealName
        Glide.with(applicationContext)
            .load(mealImg)
            .into(binding.imgMealDetail)
    }

    private fun getMealInformationFromIntent() {
        val intent = intent
        mealId = intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName = intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealImg = intent.getStringExtra(HomeFragment.MEAL_THUMB)!!
    }

    private fun loadingCase(){
        binding.progressBar.visibility = View.VISIBLE
        binding.tvMealDetailArea.visibility = View.INVISIBLE
        binding.tvInstructionDetails.visibility = View.INVISIBLE
        binding.imgYouTube.visibility = View.INVISIBLE
        binding.tvMealDetailCategory.visibility = View.INVISIBLE
        binding.floatingFavIcon.visibility = View.INVISIBLE
    }

    private fun responceCase(){
        binding.progressBar.visibility = View.INVISIBLE
        binding.tvMealDetailArea.visibility = View.VISIBLE
        binding.tvInstructionDetails.visibility = View.VISIBLE
        binding.imgYouTube.visibility = View.VISIBLE
        binding.tvMealDetailCategory.visibility = View.VISIBLE
        binding.floatingFavIcon.visibility = View.VISIBLE
    }
}