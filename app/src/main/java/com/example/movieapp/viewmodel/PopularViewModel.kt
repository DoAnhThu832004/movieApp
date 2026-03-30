package com.example.movieapp.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.movieapp.model.ApiService
import androidx.compose.runtime.State
import androidx.lifecycle.viewModelScope
import com.example.movieapp.model.Response.NowPlaying
import com.example.movieapp.model.Response.Popular
import kotlinx.coroutines.launch

class PopularViewModel(
    private val apiService: ApiService
): ViewModel() {
    private val _popularState = mutableStateOf(PopularState())
    val popularState: State<PopularState> = _popularState

    fun getPopular(apiKey: String) {
        viewModelScope.launch {
            _popularState.value = _popularState.value.copy(
                isLoading = true,
                error = null
            )
            try {
                val response = apiService.getPopular(apiKey)
                _popularState.value = _popularState.value.copy(
                    popular = response.results,
                    isLoading = false,
                    error = null
                )
            } catch (e: Exception) {
                _popularState.value = _popularState.value.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }
    data class PopularState(
        val popular: List<Popular>? = null,
        val isLoading: Boolean = false,
        val error: String? = null
    )
}
