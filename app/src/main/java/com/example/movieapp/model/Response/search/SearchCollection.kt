package com.example.movieapp.model.Response.search

data class SearchCollection(
    val adult: Boolean,
    val backdrop_path: String,
    val id: Int,
    val name: String,
    val original_language: String,
    val original_name: String,
    val overview: String,
    val poster_path: String,
)
