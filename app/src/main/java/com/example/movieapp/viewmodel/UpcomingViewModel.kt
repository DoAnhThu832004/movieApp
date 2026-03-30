package com.example.movieapp.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.model.ApiService
import com.example.movieapp.model.Response.NowPlaying
import com.example.movieapp.model.Response.UpComing
import kotlinx.coroutines.launch

class UpcomingViewModel(
    private val apiService: ApiService
): ViewModel() {
    private val _upComingState = mutableStateOf(UpcomingState())
    val upComingState: State<UpcomingState> = _upComingState
    fun getUpcoming(apiKey: String) {
        viewModelScope.launch {
            _upComingState.value = _upComingState.value.copy(
                isLoading = true,
                error = null
            )
            try {
                val response = apiService.getUpcoming(apiKey)
                _upComingState.value = _upComingState.value.copy(
                    upcoming = response.results,
                    isLoading = false,
                    error = null
                )
            } catch (e: Exception) {
                _upComingState.value = _upComingState.value.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }
    data class UpcomingState(
        val upcoming: List<UpComing>? = null,
        val isLoading: Boolean = false,
        val error: String? = null
    )
}