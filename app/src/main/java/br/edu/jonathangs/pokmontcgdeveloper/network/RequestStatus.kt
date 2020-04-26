package br.edu.jonathangs.pokmontcgdeveloper.network

sealed class RequestStatus<out T> {

    data class Success<out T>(val data: T? = null): RequestStatus<T>()

    data class Failure<out T>(val cause: Throwable): RequestStatus<T>()

}