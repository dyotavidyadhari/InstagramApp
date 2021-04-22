package com.example.instagramkita

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.instagramkita.model.post
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var users: MutableList<post> = mutableListOf()
        val ref: DatabaseReference = FirebaseDatabase.getInstance("https://instagramkita-bfda8-default-rtdb.firebaseio.com/").getReference("post")


        ref.addValueEventListener(object :ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                Log.w("error=","Error bund")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(item in snapshot.children){

                    }
                }
            }

        })
    }
}