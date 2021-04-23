package com.example.instagramkita

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.instagramkita.model.user
import com.example.instagramkita.model.userPost
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.activity_upload.*
import java.text.SimpleDateFormat
import java.util.*


class Upload : AppCompatActivity() {
    lateinit var imguri : Uri
    lateinit var imageName: String
    lateinit var id: String
    lateinit var imgUser: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)

        id = intent.getStringExtra("id").toString()
        imgUser = intent.getStringExtra("img_path").toString()

        imgupload.setOnClickListener {
           choosepicture()
        }
        cancel_upload.setOnClickListener setOnNavigationItemSelectedListener@{
            onBackPressed()
        }

        upload_post.setOnClickListener {
            //Toast.makeText(this, "Uploading . . .${imguri}", Toast.LENGTH_LONG).show()
            uploadImage()
        }

    }

    private fun uploadImage() {
        val pd = ProgressDialog(this)
        pd.setTitle("Uploading image...")
        pd.show()

        val filename = UUID.randomUUID().toString()
        val storage = FirebaseStorage.getInstance("gs://instagramkita-bfda8.appspot.com").getReference("images/$filename")
        storage.putFile(imguri)
            .addOnProgressListener { pr ->
                val progress = (100.00 * pr.bytesTransferred / pr.totalByteCount)
                pd.setMessage("Uploaded ${progress.toInt()}%")

            }
                .addOnSuccessListener {
                    storage.downloadUrl.addOnSuccessListener {
                        uploadPost(it.toString())
                    }
                }
    }

    private fun uploadPost(imageUrl: String) {
        val postRef = FirebaseDatabase.getInstance("https://instagramkita-bfda8-default-rtdb.firebaseio.com/").getReference("user-post/${UUID.randomUUID().toString()}")
        val userRef = FirebaseDatabase.getInstance("https://instagramkita-bfda8-default-rtdb.firebaseio.com/").getReference("userprofil")
        val calendar: Calendar = Calendar.getInstance()
        val simpleDateFormat = SimpleDateFormat("EEEE, dd-MMM-yyyy")
        val dateTime = simpleDateFormat.format(calendar.time)

        val userPost = userPost(
                nama = id,
                caption = upload_caption.text.toString(),
                tag = upload_tag.text.toString(),
                image = imgUser,
                imagefeed = imageUrl,
                tanggal = dateTime
        )
        postRef.setValue(userPost).addOnSuccessListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@Upload, MainActivity::class.java)
        intent.putExtra("id", id)
        intent.putExtra("img_path", imgUser)
        startActivity(intent)
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