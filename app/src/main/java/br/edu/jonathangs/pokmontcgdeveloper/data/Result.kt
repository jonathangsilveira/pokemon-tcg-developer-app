package br.edu.jonathangs.pokmontcgdeveloper.data

sealed class Result<out T> {

    data class Success<out T>(val data: T? = null): Result<T>()

    data class Failure(val throwable: Throwable): Result<Nothing>()

}