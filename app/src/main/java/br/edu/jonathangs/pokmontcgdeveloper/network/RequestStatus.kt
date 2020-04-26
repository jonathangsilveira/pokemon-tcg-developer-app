package br.edu.jonathangs.pokmontcgdeveloper.network

import br.edu.jonathangs.pokmontcgdeveloper.network.data.Error

sealed class RequestStatus<out T> {

    data class Success<out T>(val data: T? = null): RequestStatus<T>()

    sealed class Failure<out T>: RequestStatus<T>() {

        data class Response<out T>(val errorBody: Error? = null): Failure<T>()

        data class Undefined<out T>(val cause: Throwable): Failure<T>()

    }

}