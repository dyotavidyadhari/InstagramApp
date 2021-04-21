package com.example.instagramkita.Adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.instagramkita.model.post

class MainAdapter(context: Context,private val post:List<post>):RecyclerView.Adapter<MainAdapter.MyViewHolder>() {
    class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainAdapter.MyViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: MainAdapter.MyViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}