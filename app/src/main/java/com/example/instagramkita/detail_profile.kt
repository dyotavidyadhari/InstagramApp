package com.example.instagramkita

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.instagramkita.Adapter.PostinganAdapter
import com.example.instagramkita.model.userPost
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.*
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_detail_profile.*

class detail_profile : AppCompatActivity() {
    lateinit var id: String
    lateinit var imgPath: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_profile)

        id = intent.getStringExtra("id").toString()
        imgPath = intent.getStringExtra("img_path").toString()
        val foto = findViewById<CircleImageView>(R.id.foto_profil)
        val name = findViewById<TextView>(R.id.profile_username)
        val uname = findViewById<TextView>(R.id.uname_photo)

        Glide.with(this)
            .load(imgPath)
            .into(foto)
        name.text = id.toString()
        uname.text = id.toString()


        val bottomNav: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNav.selectedItemId = R.id.profile
        val userlog: DatabaseReference = FirebaseDatabase.getInstance("https://instagramkita-bfda8-default-rtdb.firebaseio.com/").getReference("user-post")
        bottomNav.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home->{
//                    var tent = Intent(this@detail_profile, MainActivity::class.java)
//                    tent.putExtra("id",id)
//                    startActivity(tent)
//                    return@setOnNavigationItemSelectedListener true
                    onBackPressed()
                }
                R.id.upload->{
                    var tent = Intent(this@detail_profile, Upload::class.java)
                    tent.putExtra("img_path", imgPath)
                    tent.putExtra("id",id)
                    startActivity(tent)
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }

        var allPostImg: MutableList<userPost> = mutableListOf()
        userlog.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                Log.w("error=","Error bund")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(item in snapshot.children){
                        if(id == item.child("nama").value.toString()){
                           var userPost: userPost? = userPost()
                            userPost?.image = item.child("image").value.toString()
                            userPost?.nama = item.child("nama").value.toString()
                            userPost?.caption = item.child("caption").value.toString()
                            userPost?.imagefeed = item.child("imagefeed").value.toString()
                            userPost?.tag = item.child("tag").value.toString()
                            userPost?.tanggal = item.child("tanggal").value.toString()


                            allPostImg.add(userPost!!)

                        }
                    }
                }
                showUserPost(allPostImg)
                post_num.text = allPostImg.size.toString()

            }
        })
    }

    private fun showUserPost(allData: MutableList<userPost>) {
        val adapter = this.let { PostinganAdapter(it, allData)}
        val layout: RecyclerView.LayoutManager = GridLayoutManager(this,3)
        rv_profil.layoutManager = layout
        rv_profil.adapter = adapter
        
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("id", id)
        intent.putExtra("img_path", imgPath)
        startActivity(intent)
    }
}