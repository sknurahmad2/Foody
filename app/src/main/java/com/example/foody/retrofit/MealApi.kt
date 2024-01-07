package com.example.foody.retrofit

import com.example.foody.pojo.Meal_list
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {

    @GET("random.php")
    fun getRandomMeal():Call<Meal_list>

    @GET("lookup.php?")
    fun getMealDeatils(@Query("i") id:String) : Call<Meal_list>
}