package com.example.movieapp.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.model.ApiService
import com.example.movieapp.model.Response.NowPlaying
import kotlinx.coroutines.launch

class NowPlayingViewModel(
    private val apiService: ApiService
): ViewModel() {
    private val _nowPlayingState = mutableStateOf(NowPlayingState())
    val nowPlayingState: State<NowPlayingState> = _nowPlayingState
    fun getNowPlaying(apiKey: String) {
        viewModelScope.launch {
            _nowPlayingState.value = _nowPlayingState.value.copy(
                isLoading = true,
                error = null
            )
            try {
                val response = apiService.getNowPlaying(apiKey)
                _nowPlayingState.value = _nowPlayingState.value.copy(
                    nowPlaying = response.results,
                    isLoading = false,
                    error = null
                )
            } catch (e: Exception) {
                _nowPlayingState.value = _nowPlayingState.value.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }
    data class NowPlayingState(
        val nowPlaying: List<NowPlaying>? = null,
        val isLoading: Boolean = false,
        val error: String? = null
    )
}