package br.edu.jonathangs.pokmontcgdeveloper.domain

import br.edu.jonathangs.pokmontcgdeveloper.network.RequestStatus

sealed class LoadingState<T>(
    val data: T? = null,
    val message: String? = null
) {

    class Loading<T>(data: T? = null): LoadingState<T>(data, null)

    class Loaded<T>(data: T?, message: String?): LoadingState<T>(data, message)

    class Error<T>(data: T?, message: String?): LoadingState<T>(data, message)

}

sealed class State
sealed class LoadState<out T>: State() {

    class InProgress<out T>: LoadState<T>()

    data class Success<out T>(val data: T? = null): LoadState<T>()

    data class Failure<out T>(val cause: Throwable): LoadState<T>()

    companion object {

        fun <T> inProgress() =
            InProgress<T>()

        fun <T> success(data: T? = null) =
            Success(data)

        fun <T> failure(cause: Throwable) =
            Failure<T>(
                cause
            )

    }

}

sealed class ListState<out T>: LoadState<List<T>>() {

    class InProgress<out T>: ListState<T>()

    data class Success<out T, out F>(
        val data: List<T>?,
        val apiFailure: RequestStatus.Failure<F>?
    ): ListState<T>()

    data class Failure<out T>(val cause: Throwable): ListState<T>()

    companion object {

        fun <T> inProgress() =
            InProgress<T>()

        fun <T, F> success(
            data: List<T>? = null,
            apiFailure: RequestStatus.Failure<F>? = null
        ) = Success(
            data,
            apiFailure
        )

        fun <T> failure(cause: Throwable) =
            Failure<T>(
                cause
            )

    }

}