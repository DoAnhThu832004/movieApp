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
import com.example.movieapp.model.Response.movie.Movie
import com.example.movieapp.model.Response.search.SearchCollection
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
        @Path("collectionId") collectionId: Int,
        @Query("api_key") apiKey: String
    ): Collection
    @GET("person/popular")
    suspend fun getPerson(
        @Query("api_key") apiKey: String
    ): ApiResponse<Person>
    @GET("person/{person_id}")
    suspend fun getPersonDetail(
        @Path("person_id") personId: Int,
        @Query("api_key") apiKey: String
    ): PersonDetail
    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): Movie
    @GET("search/collection")
    suspend fun searchCollection(
        @Query("query") query: String,
        @Query("api_key") apiKey: String
    ): ApiResponse<SearchCollection>
}
private const val BASE_URL = "https://api.themoviedb.org/3/"
val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()


val apiService: ApiService = retrofit.create(ApiService::class.java)