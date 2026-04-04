package com.example.movieapp.model

import com.example.movieapp.model.Response.ApiResponse
import com.example.movieapp.model.Response.Collection
import com.example.movieapp.model.Response.NowPlaying
import com.example.movieapp.model.Response.NowResponse
import com.example.movieapp.model.Response.Person
import com.example.movieapp.model.Response.PersonDetail
import com.example.movieapp.model.Response.Popular
import com.example.movieapp.model.Response.TopRated
import com.example.movieapp.model.Response.Trending
import com.example.movieapp.model.Response.UpComing
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
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
    @GET("movie/popular")
    suspend fun getPopular(
        @Query("api_key") apiKey: String
    ): ApiResponse<Popular>
    @GET("movie/top_rated")
    suspend fun getTopRated(
        @Query("api_key") apiKey: String
    ): ApiResponse<TopRated>
    @GET("movie/upcoming")
    suspend fun getUpcoming(
        @Query("api_key") apiKey: String
    ): NowResponse<UpComing>
    @GET("collection/{collectionId}")
    suspend fun getCollection(
        @Query("api_key") apiKey: String,
        @Query("collectionId") collectionId: Int
    ): Collection
    @GET("person/popular")
    suspend fun getPerson(
        @Query("api_key") apiKey: String
    ): ApiResponse<Person>
    @GET("person/{person_id}")
    suspend fun getPersonDetail(
        @Query("api_key") apiKey: String,
        @Path("person_id") personId: Int
    ): PersonDetail
}
private const val BASE_URL = "https://api.themoviedb.org/3/"
val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()


val apiService: ApiService = retrofit.create(ApiService::class.java)