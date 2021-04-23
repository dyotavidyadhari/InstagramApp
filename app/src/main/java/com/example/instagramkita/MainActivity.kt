package com.example.instagramkita

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.instagramkita.Adapter.FeedAdapter
import com.example.instagramkita.model.post
import com.example.instagramkita.model.userPost
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.*
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var id = intent.getStringExtra("id")
        val imgPath = intent.getStringExtra("img_path")
        val circle = findViewById<CircleImageView>(R.id.icon_story)
        val uname = findViewById<TextView>(R.id.uname_feeds)

        Glide.with(this)
            .load(imgPath)
            .into(circle)

        uname.text= id.toString()

        val bottomNav: BottomNavigationView = findViewById(R.id.main_bottom_nav)

        bottomNav.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.upload->{
                    var tent = Intent(this, Upload::class.java)
                    tent.putExtra("id",id)
                    tent.putExtra("img_path", imgPath)
                    startActivity(tent)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.profile->{
                    var tent = Intent(this, detail_profile::class.java)
                    tent.putExtra("id",id)
                    tent.putExtra("img_path", imgPath)
                    startActivity(tent)
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }

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
                        itemPost?.image = item.child("image").value.toString()
                        itemPost?.nama = item.child("nama").value.toString()
                        itemPost?.caption = item.child("caption").value.toString()
                        itemPost?.imagefeed = item.child("imagefeed").value.toString()
                        itemPost?.tag = item.child("tag").value.toString()
                        itemPost?.tanggal = item.child("tanggal").value.toString()
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