package com.example.instagramkita

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.instagramkita.model.user
import com.example.instagramkita.model.userPost
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.*
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_detail_profile.*

class detail_profile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_profile)

        var id = intent.getStringExtra("id")

        val bottomNav: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNav.selectedItemId = R.id.profile
        val userlog: DatabaseReference = FirebaseDatabase.getInstance("https://instagramkita-bfda8-default-rtdb.firebaseio.com/").getReference("user-post")
        bottomNav.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home->{
                    var tent = Intent(this@detail_profile, MainActivity::class.java)
                    tent.putExtra("id",id)
                    startActivity(tent)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.upload->{
                    var tent = Intent(this@detail_profile, Upload::class.java)
                    tent.putExtra("id",id)
                    startActivity(tent)
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }

        userlog.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                Log.w("error=","Error bund")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(item in snapshot.children){
                        if(id == item.child("nama").value.toString()){
                           var userPost: userPost? = userPost()
                            userPost?.imageUser = item.child("image").value.toString()
                            userPost?.nama = item.child("nama").value.toString()
                            userPost?.caption = item.child("caption").value.toString()
                            userPost?.image = item.child("imagefeed").value.toString()
                            userPost?.tag = item.child("tag").value.toString()
                            userPost?.tanggal = item.child("tanggal").value.toString()
                            val foto = findViewById<CircleImageView>(R.id.foto_profil)
                            val name = findViewById<TextView>(R.id.profile_username)
                            val uname = findViewById<TextView>(R.id.uname_photo)

                            Glide.with(this@detail_profile).load(userPost?.imageUser).into(foto)
                            name.text = userPost?.nama
                            uname.text = userPost?.nama
                        }
                    }
                }
            }
        })

    }
}