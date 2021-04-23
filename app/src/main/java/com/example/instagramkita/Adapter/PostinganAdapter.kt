package com.example.instagramkita.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.instagramkita.R
import com.example.instagramkita.model.userPost
import kotlinx.android.synthetic.main.postingan.view.*

class PostinganAdapter(context: Context, private val poster: MutableList<userPost>): RecyclerView.Adapter<PostinganAdapter.MyViewHoleder>() {
    var konteks:Context
    init {
        konteks = context
    }
    class MyViewHoleder(view: View): RecyclerView.ViewHolder(view) {
        private val img = view.post

        fun bindppost(context: Context, posts: userPost){
            Glide.with(context).load(posts.imagefeed).into(img)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostinganAdapter.MyViewHoleder {
        return MyViewHoleder(LayoutInflater.from(parent.context).inflate(R.layout.postingan,parent,false))
    }

    override fun getItemCount(): Int = poster.size

    override fun onBindViewHolder(holder: PostinganAdapter.MyViewHoleder, position: Int) {
        holder.bindppost(konteks,poster[position])
    }
}