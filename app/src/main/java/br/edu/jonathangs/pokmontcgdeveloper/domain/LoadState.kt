package br.edu.jonathangs.pokmontcgdeveloper.domain

sealed class LoadState<out T> {
    object InProgress: LoadState<Nothing>()
    data class Success<out T>(val data: T? = null): LoadState<T>()
    data class Exception(val cause: Throwable): LoadState<Nothing>()
}