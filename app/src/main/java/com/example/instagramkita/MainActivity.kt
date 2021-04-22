package com.example.instagramkita

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.instagramkita.Adapter.FeedAdapter
import com.example.instagramkita.model.post
import com.example.instagramkita.model.userPost
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val yo: BottomNavigationView = findViewById(R.id.main_bottom_nav)

        var users: MutableList<post> = mutableListOf()
        val posRef: DatabaseReference = FirebaseDatabase.getInstance("https://instagramkita-bfda8-default-rtdb.firebaseio.com/").getReference("post")
        val userRef: DatabaseReference = FirebaseDatabase.getInstance("https://instagramkita-bfda8-default-rtdb.firebaseio.com/").getReference("user-post")

        var allPost: MutableList<userPost> = mutableListOf()
        userRef.addValueEventListener(object :ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                Log.w("error=","Error bund")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(item in snapshot.children){

                        var itemPost: userPost? = userPost()
                        itemPost?.imageUser = item.child("image").value.toString()
                        itemPost?.nama = item.child("nama").value.toString()
                        posRef.child(item.key.toString()).get().addOnSuccessListener(this@MainActivity, OnSuccessListener {
                            val ganteng: post? = it.getValue(post::class.java)

                            itemPost?.caption = ganteng?.caption
                            itemPost?.image = ganteng?.image
                            itemPost?.tag = ganteng?.tag
                            itemPost?.tanggal = ganteng?.tanggal
                        })
                        itemPost?.let { allPost.add(it) }
                    }
                }
                showPost(allPost)
            }

        })
    }

    private fun showPost(allPost: List<userPost>) {
        val adapter = this.let { FeedAdapter(it, allPost) }
        val layout: RecyclerView.LayoutManager =LinearLayoutManager(this)
        main_feed_recycler.layoutManager = layout
        main_feed_recycler.adapter = adapter
    }
}