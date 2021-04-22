package com.example.instagramkita

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Button
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

        if (!networkCheck()) {
            val dialog = Dialog(this)
            dialog.setContentView(R.layout.custom_connectivity)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                dialog.window!!.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
                dialog.window!!.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            }
            val dialogOk = dialog.findViewById<Button>(R.id.main_cancel).setOnClickListener{
                dialog.dismiss()
                finish()
            }
            dialog.show()
        }

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

    private fun networkCheck(): Boolean {
        val connManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connManager.activeNetworkInfo
        return networkInfo!=null && networkInfo.isConnected
    }
}