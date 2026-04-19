package com.example.eventticket.viewmodel

sealed interface ScreenState<out T> {
    data object Loading : ScreenState<Nothing>
    data class Ready<T>(val data: T) : ScreenState<T>
    data class Empty(val message: String) : ScreenState<Nothing>
    data class Error(val message: String) : ScreenState<Nothing>
}
