package com.example.instagramkita

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class Upload : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)

        findViewById<ImageView>(R.id.imgupload).setOnClickListener {
            startActivity(Intent(this, FileChoose::class.java))
        }
    }
}