package com.example.foody.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.foody.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnNav = findViewById<BottomNavigationView>(R.id.btm_nav)
        val hostfragment = findNavController(R.id.host_fragment)

        btnNav.setupWithNavController(hostfragment)
    }
}