package com.example.instagramkita

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_detail_profile.*

class detail_profile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_profile)



        val bottomNav: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNav.selectedItemId


        bottomNav.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home->{
                    startActivity(Intent(this@detail_profile, MainActivity::class.java))
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.upload->{
                    startActivity(Intent(this@detail_profile, Upload::class.java))
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }


    }
}