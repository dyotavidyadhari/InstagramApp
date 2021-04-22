package com.example.instagramkita.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.instagramkita.R
import com.example.instagramkita.model.post
import com.example.instagramkita.model.user
import com.example.instagramkita.model.userPost
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.feed.view.*



class FeedAdapter (context: Context,private val posts: List<userPost>): RecyclerView.Adapter<FeedAdapter.MyViewHolder>(){
    var konteks:Context
    init {
        konteks = context
    }

    class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {


        private  val img = view.feed_photo
        private val caption = view.user_caption
        private val uname = view.feed_uname
        private val imgprofile  = view.feed_profile
        private val txttag = view.txttag
        private val usecmnt = view.userid_comment

        fun bindholder(context: Context,posted: userPost){
            Glide.with(context).load(posted.image).into(img)
            Glide.with(context).load(posted.imageUser).into(imgprofile)
            uname.text = posted.nama
            usecmnt.text = posted.nama
            caption.text = posted.caption
            img.setOnClickListener{
                txttag.text = posted.tag
                txttag.isVisible = true
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedAdapter.MyViewHolder {
        return MyViewHolder(LayoutInflater.from(konteks).inflate(R.layout.feed,parent,false))
    }

    override fun getItemCount(): Int = posts.size

    override fun onBindViewHolder(holder: FeedAdapter.MyViewHolder, position: Int) {
            holder.bindholder(konteks,posts[position])
    }

}