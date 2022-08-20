package com.example.abbreviation.util

sealed class UiState{
    object Loading: UiState()
    data class Success<T>(val meaningResponse: T): UiState()
    data class Error(val error: Throwable): UiState()
}
