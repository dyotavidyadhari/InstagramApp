package com.example.instagramkita

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.instagramkita.model.post
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var users: MutableList<post> = mutableListOf()
        val ref: DatabaseReference = FirebaseDatabase.getInstance("https://instagramkita-bfda8-default-rtdb.firebaseio.com/").getReference("post")

    }
}