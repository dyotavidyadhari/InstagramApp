package com.example.instagramkita

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
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
                    connectivity()
                    if(snapshot.exists()){
                        for(item in snapshot.children){
                            if(id.text.toString() == item.child("namapengguna").value.toString() && pass.text.toString() == item.child("password").value.toString()){
                                val tent = Intent(this@Login,MainActivity::class.java)
                                tent.putExtra("id",id.text.toString())
                                tent.putExtra("img_path", item.child("img").value.toString())
                                startActivity(tent)
                            }
                        }
                    }
                }
            })
        })
    }

    private fun connectivity() {
        if (!networkCheck()) {
            val dialog = Dialog(this)
            dialog.setContentView(R.layout.custom_connectivity)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog.window!!.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            }
            val dialogOk = dialog.findViewById<Button>(R.id.main_cancel).setOnClickListener{
                dialog.dismiss()
                finish()
            }
            dialog.show()
        }
    }

    private fun networkCheck(): Boolean {
        val connManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connManager.activeNetworkInfo
        return networkInfo!=null && networkInfo.isConnected
    }
}