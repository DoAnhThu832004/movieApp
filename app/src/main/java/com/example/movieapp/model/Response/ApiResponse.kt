package com.example.movieapp.model.Response

data class ApiResponse<T>(
    val page: Int,
    val results: List<T>,
    val total_pages: Int,
    val total_results: Int
)
