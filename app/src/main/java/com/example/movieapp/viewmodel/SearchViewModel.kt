package com.example.movieapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.model.ApiService
import com.example.movieapp.model.Response.search.SearchCollection
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import kotlin.math.truncate

class SearchViewModel(
    private val apiService: ApiService
): ViewModel() {
    private val _collection = MutableStateFlow<List<SearchCollection>>(emptyList())
    val collection: StateFlow<List<SearchCollection>> = _collection.asStateFlow()
    private var searchJob: Job? = null
    fun onQueryChanged(query: String,api_key: String) {
        searchJob?.cancel()

        if(query.isBlank()) {
            clearSuggestions()
            return
        }
        searchJob = viewModelScope.launch {
            delay(500)
            performSearch(query,api_key)
        }
    }
    private suspend fun performSearch(query: String, apiKey: String) {
        try {
            val response = apiService.searchCollection(query, apiKey)
            _collection.value = response.results
        } catch (e: Exception) {
            _collection.value = emptyList()
        }
    }
    fun clearSuggestions() {
        searchJob?.cancel()
        _collection.value = emptyList()
    }
}