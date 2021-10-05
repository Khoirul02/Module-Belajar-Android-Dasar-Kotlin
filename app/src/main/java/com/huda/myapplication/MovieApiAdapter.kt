package com.huda.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.huda.myapplication.model.ResultsItem

class MovieApiAdapter (val results : ArrayList<ResultsItem>) : RecyclerView.Adapter<MovieApiAdapter.ViewHolder>() {


    fun setData(data : List<ResultsItem>){
        results.clear()
        results.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieApiAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_movie, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MovieApiAdapter.ViewHolder, position: Int) {
        val data = results[position]
        Glide.with(holder.itemView).load("https://www.themoviedb.org/t/p/w220_and_h330_face${data.posterPath}").apply(RequestOptions().override(320,320)).into(holder.image)
        holder.judul.text = data.title
        holder.kategori.text = "Vote : ${data.voteAverage.toString()}"
        holder.klik.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra("ID", data.id.toString())
            holder.itemView.context.startActivity(intent)
//            Toast.makeText(holder.itemView.context, data.id!!.toString(), Toast.LENGTH_LONG).show()
        }
    }

    override fun getItemCount(): Int {
        return results.size
    }
    inner class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
        val judul : TextView = itemView.findViewById(R.id.tv_list_movie)
        val kategori : TextView = itemView.findViewById(R.id.tv_list_kategori_movie)
        val image : ImageView = itemView.findViewById(R.id.iv_foto_list_movie)
        val klik : CardView = itemView.findViewById(R.id.cv_movie)
    }
}