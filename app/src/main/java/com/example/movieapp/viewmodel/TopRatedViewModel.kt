package com.example.movieapp.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.model.ApiService
import com.example.movieapp.model.Response.NowPlaying
import com.example.movieapp.model.Response.TopRated
import kotlinx.coroutines.launch

class TopRatedViewModel(
    private val apiService: ApiService
): ViewModel() {
    private val _topRatedState = mutableStateOf(TopRatedState())
    val topRatedState: State<TopRatedState> = _topRatedState
    fun getTopRated(apiKey: String) {
        viewModelScope.launch {
            _topRatedState.value = _topRatedState.value.copy(
                isLoading = true,
                error = null
            )
            try {
                val response = apiService.getTopRated(apiKey)
                _topRatedState.value = _topRatedState.value.copy(
                    topRated = response.results,
                    isLoading = false,
                    error = null
                )
            } catch (e: Exception) {
                _topRatedState.value = _topRatedState.value.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }
    data class TopRatedState(
        val topRated: List<TopRated>? = null,
        val isLoading: Boolean = false,
        val error: String? = null
    )
}