package com.example.instagramkita

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.instagramkita.model.user
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_detail_profile.*

class detail_profile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_profile)

        var id = intent.getStringExtra("id")

        val bottomNav: BottomNavigationView = findViewById(R.id.bottom_navigation)
        val userlog: DatabaseReference = FirebaseDatabase.getInstance("https://instagramkita-bfda8-default-rtdb.firebaseio.com/").getReference("userprofile")
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
                        if(id == item.child("namapengguna").value.toString()){
                           var userprofile: user? = user()


                        }
                    }
                }
            }
        })

    }
}