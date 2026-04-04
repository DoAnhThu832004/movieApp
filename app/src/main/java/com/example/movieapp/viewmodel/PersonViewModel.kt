package com.example.movieapp.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.movieapp.model.ApiService
import com.example.movieapp.model.Response.Person
import com.example.movieapp.model.Response.PersonDetail
import kotlinx.coroutines.launch

class PersonViewModel(
    private val apiService: ApiService
): ViewModel() {
    private val _personState = mutableStateOf(PersonState())
    val personState: State<PersonState> = _personState
    private val _personDetailState = mutableStateOf(PersonDetailState())
    val personDetailState: State<PersonDetailState> = _personDetailState

    fun getPerson(apiKey: String) {
        viewModelScope.launch {
            _personState.value = _personState.value.copy(
                isLoading = true,
                error = null
            )
            try {
                val response = apiService.getPerson(apiKey)
                _personState.value = _personState.value.copy(
                    persons = response.results,
                    isLoading = false,
                    error = null
                )
            } catch (e: Exception) {
                _personState.value = _personState.value.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }
    fun getPersonDetail(apiKey: String, personId: Int) {
        viewModelScope.launch {
            _personDetailState.value = _personDetailState.value.copy(
                isLoading = true,
                error = null
            )
            try {
                val response = apiService.getPersonDetail(apiKey, personId)
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
    data class PersonState(
        val persons: List<Person>? = null,
        val isLoading: Boolean = false,
        val error: String? = null
    )
}