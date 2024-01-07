package com.example.foody.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foody.pojo.Meal
import com.example.foody.pojo.Meal_list
import com.example.foody.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealViewModel():ViewModel() {
    private var mutableLiveData = MutableLiveData<Meal>()

    fun getMealDeatils(id:String){
        RetrofitInstance.api.getMealDeatils(id).enqueue(object :Callback<Meal_list>{
            override fun onResponse(call: Call<Meal_list>, response: Response<Meal_list>) {
                if(response.body()!=null){
                    mutableLiveData.value = response.body()!!.meals[0]
                }else{
                    return
                }
            }

            override fun onFailure(call: Call<Meal_list>, t: Throwable) {
                Log.d("Faliure",t.message.toString())
            }
        })
    }

    fun observerMealDeatilListLiveData():LiveData<Meal>{
        return mutableLiveData
    }
}