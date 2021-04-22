package com.example.instagramkita

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.instagramkita.model.post
import com.example.instagramkita.model.userPost
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var users: MutableList<post> = mutableListOf()
        val posRef: DatabaseReference = FirebaseDatabase.getInstance("https://instagramkita-bfda8-default-rtdb.firebaseio.com/").getReference("post")
        val userRef: DatabaseReference = FirebaseDatabase.getInstance("https://instagramkita-bfda8-default-rtdb.firebaseio.com/").getReference("user-post")

        userRef.addValueEventListener(object :ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                Log.w("error=","Error bund")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(item in snapshot.children){

                        var itemPost: userPost? = userPost()
                        posRef.child(item.key.toString()).get().addOnSuccessListener(this@MainActivity, OnSuccessListener {
                            val ganteng: post? = it.getValue(post::class.java)

                            itemPost?.caption = ganteng?.caption
                            itemPost
                        })
                    }
                }
            }

        })
    }
}