package com.example.movieapp.viewmodel

import androidx.compose.runtime.Recomposer
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.movieapp.model.ApiService
import com.example.movieapp.model.Response.Collection
import androidx.compose.runtime.State
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class CollectionViewModel(
    private val apiService: ApiService
): ViewModel() {
    private val _collectionState = mutableStateOf(CollectionState())
    val collectionState: State<CollectionState> = _collectionState
    fun getCollection(apiKey: String, collectionId: Int) {
        viewModelScope.launch {
            _collectionState.value = _collectionState.value.copy(
                isLoading = true,
                error = null
            )
            try {
                val response = apiService.getCollection(apiKey, collectionId)
                _collectionState.value = _collectionState.value.copy(
                    collection = response,
                    isLoading = false,
                    error = null
                )
            } catch (e: Exception) {
                _collectionState.value = _collectionState.value.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }
    data class CollectionState(
        val collection: Collection? = null,
        val isLoading: Boolean = false,
        val error: String? = null
    )
}