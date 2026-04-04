package com.example.movieapp.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.movieapp.model.ApiService
import com.example.movieapp.model.Response.PersonDetail
import kotlinx.coroutines.launch

class PersonDetailViewModel(
    private val apiService: ApiService
): ViewModel() {
    private val _personDetailState = mutableStateOf(PersonDetailState())
    val personDetailState: State<PersonDetailState> = _personDetailState
    fun getPersonDetail(apiKey: String, personId: Int) {
        viewModelScope.launch {
            _personDetailState.value = _personDetailState.value.copy(
                isLoading = true,
                error = null
            )
            try {
                val response = apiService.getPersonDetail(personId,apiKey)
                _personDetailState.value = _personDetailState.value.copy(
                    personsDetail = response,
                    isLoading = false,
                    error = null
                )
            } catch (e: Exception) {
                _personDetailState.value = _personDetailState.value.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }
    data class PersonDetailState(
        val personsDetail: PersonDetail? = null,
        val isLoading: Boolean = false,
        val error: String? = null
    )
}