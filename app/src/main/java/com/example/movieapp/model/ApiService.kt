package com.example.movieapp.model

import com.example.movieapp.model.Response.ApiResponse
import com.example.movieapp.model.Response.NowPlaying
import com.example.movieapp.model.Response.NowResponse
import com.example.movieapp.model.Response.Popular
import com.example.movieapp.model.Response.TopRated
import com.example.movieapp.model.Response.Trending
import com.example.movieapp.model.Response.UpComing
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {
    @GET("trending/movie/day")
    suspend fun getTrendingMovies(
        @Query("api_key") apiKey: String
    ): ApiResponse<Trending>
    @GET("movie/now_playing")
    suspend fun getNowPlaying(
        @Query("api_key") apiKey: String
    ): NowResponse<NowPlaying>
    @GET("movie/top_rated")
    suspend fun getTopRated(
        @Query("api_key") apiKey: String
    ): ApiResponse<TopRated>
    @GET("movie/upcoming")
    suspend fun getUpcoming(
        @Query("api_key") apiKey: String
    ): NowResponse<UpComing>
    @GET("movie/popular")
    suspend fun getPopular(
        @Query("api_key") apiKey: String
    ): ApiResponse<Popular>
}
val retrofit = Retrofit.Builder()
    .baseUrl("https://api.themoviedb.org/3/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val apiService = retrofit.create(ApiService::class.java)