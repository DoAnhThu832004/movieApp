package com.example.movieapp.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.movieapp.model.ApiService
import com.example.movieapp.model.Response.movie.Movie
import androidx.compose.runtime.State
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MovieViewModel(
    private val apiService: ApiService
): ViewModel() {
    private val _movieState = mutableStateOf(MovieState())
    val movieState: State<MovieState> = _movieState
    fun getMovieDetail(movieId: Int, apiKey: String) {
        viewModelScope.launch {
            _movieState.value = _movieState.value.copy(
                isLoading = true,
                error = null
            )
            try {
                val response = apiService.getMovieDetail(movieId,apiKey)
                _movieState.value = _movieState.value.copy(
                    movie = response,
                    isLoading = false,
                    error = null
                )
            } catch (e: Exception) {
                _movieState.value = _movieState.value.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }
    data class MovieState(
        val movie: Movie? = null,
        val isLoading: Boolean = false,
        val error: String? = null
    )
}