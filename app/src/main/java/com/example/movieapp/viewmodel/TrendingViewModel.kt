package com.example.movieapp.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.movieapp.model.ApiService
import com.example.movieapp.model.Response.Trending
import androidx.compose.runtime.State
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class TrendingViewModel(
    private val apiService: ApiService
) : ViewModel() {
    private val _trendState = mutableStateOf(TrendingState())
    val trendState: State<TrendingState> = _trendState
    fun getTrending(apiKey: String) {
        viewModelScope.launch {
            _trendState.value = _trendState.value.copy(
                isLoading = true,
                error = null
            )
            try {
                val response = apiService.getTrendingMovies(apiKey)
                _trendState.value = _trendState.value.copy(
                    trendingMovies = response.results,
                    isLoading = false,
                    error = null
                )
            } catch (e: Exception) {
                _trendState.value = _trendState.value.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }
    data class TrendingState(
        val trendingMovies: List<Trending>? = null,
        val isLoading: Boolean = false,
        val error: String? = null
    )
}