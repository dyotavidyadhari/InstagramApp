package com.example.instagramkita.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.instagramkita.R
import com.example.instagramkita.model.post

class FeedAdapter (context: Context,private val posts: List<post>): RecyclerView.Adapter<FeedAdapter.MyViewHolder>(){
    var konteks:Context
    init {
        konteks = context
    }

    class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {

        fun bindholder(context: Context,posted:post){

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedAdapter.MyViewHolder {
        return MyViewHolder(LayoutInflater.from(konteks).inflate(R.layout.feed,parent,false))
    }

    override fun getItemCount(): Int = posts.size

    override fun onBindViewHolder(holder: FeedAdapter.MyViewHolder, position: Int) {

    }

}