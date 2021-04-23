package com.example.instagramkita

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView

class Upload : AppCompatActivity() {
    lateinit var imguri : Uri
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)

        var id = intent.getStringExtra("id")
        //val img = intent.getParcelableExtra<Uri>("uri")
        //val imgg = findViewById<ImageView>(R.id.imgupload)
        //imgg.setImageURI(img)

        findViewById<ImageView>(R.id.imgupload).setOnClickListener {
           choosepicture()
       }
        findViewById<ImageView>(R.id.cancel_upload).setOnClickListener setOnNavigationItemSelectedListener@{
            var tent = Intent(this, MainActivity::class.java)
            tent.putExtra("id",id)
            startActivity(tent)
        }

    }

    private fun choosepicture() {
        val choose = Intent(Intent.ACTION_GET_CONTENT)
        choose.setType("image/*")
        startActivityForResult(choose,1)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
         super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==1 && resultCode== RESULT_OK && data!=null){
        imguri = data.data!!
            val imgg = findViewById<ImageView>(R.id.imgupload)
            imgg.setImageURI(imguri)
          }
          }
}