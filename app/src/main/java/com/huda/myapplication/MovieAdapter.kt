package com.huda.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {
    val title = arrayOf("Movie 1", "Movie 2", "Movie 3")
    val kategori = arrayOf("Kategori 1", "Kategori 2", "Kategori 3")
    val gambar = arrayOf("https://www.themoviedb.org/t/p/w220_and_h330_face/34nDCQZwaEvsy4CFO5hkGRFDCVU.jpg"
    ,"https://www.themoviedb.org/t/p/w220_and_h330_face/ekZobS8isE6mA53RAiGDG93hBxL.jpg","https://lumiere-a.akamaihd.net/v1/images/aladdin-movie-poster_63150511.jpeg")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_movie, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieAdapter.ViewHolder, position: Int) {
        holder.judul.text = title[position]
        holder.kategori.text = kategori[position]
        Glide.with(holder.itemView).load(gambar[position]).apply(RequestOptions().override(320,320)).into(holder.image)
    }

    override fun getItemCount(): Int {
        return title.size
    }
    inner class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
        val judul : TextView = itemView.findViewById(R.id.tv_list_movie)
        val kategori : TextView = itemView.findViewById(R.id.tv_list_kategori_movie)
        val image : ImageView = itemView.findViewById(R.id.iv_foto_list_movie)
        val klik : CardView = itemView.findViewById(R.id.cv_movie)
    }
}