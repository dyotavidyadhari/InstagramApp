package com.example.instagramkita

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.instagramkita.model.user
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.*

class Login : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val userlog: DatabaseReference = FirebaseDatabase.getInstance("https://instagramkita-bfda8-default-rtdb.firebaseio.com/").getReference("userprofile")

        val id: TextInputEditText = findViewById(R.id.logusername)
        val pass: TextInputEditText = findViewById(R.id.logpassword)

        findViewById<Button>(R.id.loginbtn).setOnClickListener(View.OnClickListener {
            userlog.addValueEventListener(object : ValueEventListener{
                override fun onCancelled(error: DatabaseError) {
                    Log.w("error=","Error bund")
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()){
                        for(item in snapshot.children){
                            if(id.text.toString() == item.child("namapengguna").value.toString() && pass.text.toString() == item.child("password").value.toString()){
                                val tent = Intent(this@Login,MainActivity::class.java)
                                tent.putExtra("id",id.text.toString())
                                startActivity(tent)
                            }
                        }
                    }
                }
            })
        })

    }
}