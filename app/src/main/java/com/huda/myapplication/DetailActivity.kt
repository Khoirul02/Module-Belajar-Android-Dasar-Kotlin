package com.huda.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.huda.myapplication.model.DetailMovieResponse
import com.huda.myapplication.rest.RetrofitClient
import kotlinx.android.synthetic.main.activity_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        var id = intent.getStringExtra("ID")
//        Toast.makeText(this, id, Toast.LENGTH_LONG).show()
        getDetailMovie(id)
    }

    private fun getDetailMovie(id: String?) {
        RetrofitClient.instance.getDetailMovie(id!!.toInt())
            .enqueue(object : Callback<DetailMovieResponse>{
                override fun onResponse(
                    call: Call<DetailMovieResponse>?,
                    response: Response<DetailMovieResponse>?
                ) {
                    if (response!!.isSuccessful){
                        tv_judul.text = response.body().originalTitle
                        showDetail(response.body() )
                    }
                }

                override fun onFailure(call: Call<DetailMovieResponse>?, t: Throwable?) {
                    Toast.makeText(this@DetailActivity, "Response : ${t}", Toast.LENGTH_LONG).show()
                }

            })
    }
    fun showDetail(body: DetailMovieResponse) {
        tv_judul.text = body.originalTitle
    }
}
