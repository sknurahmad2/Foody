package com.example.easyfood.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.easyfood.R
import com.example.easyfood.databinding.ActivityMealBinding
import com.example.easyfood.fragment.HomeFragment.Companion.MEAL_ID
import com.example.easyfood.fragment.HomeFragment.Companion.MEAL_STR
import com.example.easyfood.fragment.HomeFragment.Companion.MEAL_THUMB

class MealActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMealBinding
    private var mealId = ""
    private var mealStr = ""
    private var mealThumb = ""
    private var instruction = ""
    private var ytUrl = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getMealInfoFromIntent()
        setUpViewWithMealInformation()
    }

    private fun setUpViewWithMealInformation() {
        Glide.with(applicationContext)
            .load(mealThumb)
            .into(binding.imgMealDetail)

        binding.collapsingToolbar.title = mealStr
        binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.tvContent.text = instruction
    }

    private fun getMealInfoFromIntent() {
        val tempIntent = intent

        mealId = tempIntent.getStringExtra(MEAL_ID) ?: "Null"
        mealStr = tempIntent.getStringExtra(MEAL_STR) ?: "Null"
        mealThumb = tempIntent.getStringExtra(MEAL_THUMB) ?: "Null"
        instruction = demoInstruction

    }

    private var demoInstruction = "Read the recipe thoroughly and ensure each step is clear and makes sense to you.\n" +
            "\n" +
            "Purchase your ingredients. It’s a good idea to buy several types of oats for a discussion during or after the presentation. I recommend getting at least three types of oats -- instant, rolled, and steel-cut.\n" +
            "\n" +
            "Review my warning about cooking with milk. You’ll find it in Judy’s Tips section below.\n" +
            "\n" +
            "Explore the health benefits of oats at the Whole Grain Council's website.\n" +
            "\n" +
            "Print any handouts or recipes that you want to distribute to the group.\n" +
            "\n" +
            "Practice your demonstration a few times. Try to get family or friends to watch you. Also, we are always just an email or phone call away if you need help. Contact us anytime!"
}