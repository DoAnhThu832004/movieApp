package com.example.movieapp.model.Response

data class Collection(
    val id: Int,
    val name: String,
    val original_language: String,
    val original_name: String,
    val overview: String,
    val poster_path: String,
    val backdrop_path: String,
    val parts: List<Trending>
)
