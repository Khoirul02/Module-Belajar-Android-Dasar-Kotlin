package com.huda.myapplication.rest

import com.huda.myapplication.model.DefaultResponse
import com.huda.myapplication.model.DetailMovieResponse
import com.huda.myapplication.model.MovieResponse
import com.huda.myapplication.model.ResultResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface Api {
    @GET("movie/popular?api_key=8152b136a5dad36d2ca7844f884577ba&language=en&page=1")
    fun getMovie(): Call<MovieResponse>
    @GET("movie/{movie_id}?api_key=8152b136a5dad36d2ca7844f884577ba&language=en&page=1")
    fun getDetailMovie(
        @Path("movie_id") movie_id: Int): Call<DetailMovieResponse>
    @FormUrlEncoded
    @POST("php_rest_api.php")
    fun insertTAsk(
        @Field("id") id : String,
        @Field("task_name") task_name : String,
        @Field("task_date") task_date : String,
        @Field("is_done") is_done : String,
        @Query("function") function : String
    ) : Call<DefaultResponse>

    @FormUrlEncoded
    @GET("php_rest_api.php")
    fun login(
        @Field("id") id : String,
        @Field("task_name") task_name : String,
        @Field("task_date") task_date : String,
        @Field("is_done") is_done : String,
        @Query("function") function : String
    ) : Call<DefaultResponse>

    @FormUrlEncoded
    @POST("php_rest_api.php")
    fun updateTAsk(
        @Field("id") id : String,
        @Field("task_name") task_name : String,
        @Field("task_date") task_date : String,
        @Field("is_done") is_done : String,
        @Query("function") function : String
    ) : Call<DefaultResponse>
    @FormUrlEncoded
    @POST("php_rest_api.php")
    fun deleteTAsk(
        @Field("id") id : String,
        @Query("function") function : String
    ) : Call<DefaultResponse>

    @Multipart
    @POST("images/upload.php")
    fun postImage(@Part image: MultipartBody.Part
    ): Call<ResultResponse?>

}