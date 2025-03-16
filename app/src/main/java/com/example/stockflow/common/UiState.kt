package com.example.stockflow.common

sealed interface UiState<out T>{
    data object Idle : UiState<Nothing>
    data object Loading : UiState<Nothing>
    data class Success<T>(val data: T, val message: String) : UiState<T>
    data class Failed(val message: String) : UiState<Nothing>
}