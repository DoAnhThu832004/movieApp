package com.example.movieapp.model

import com.example.movieapp.model.Response.ApiResponse
import com.example.movieapp.model.Response.Trending
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {
    @GET("trending/movie/day")
    suspend fun getTrendingMovies(
        @Query("api_key") apiKey: String
    ): ApiResponse<Trending>
}
val retrofit = Retrofit.Builder()
    .baseUrl("https://api.themoviedb.org/3/") // Nhớ có dấu gạch chéo cuối cùng
    .addConverterFactory(GsonConverterFactory.create()) // Để nó hiểu @SerializedName
    .build()

val apiService = retrofit.create(ApiService::class.java)