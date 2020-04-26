package br.edu.jonathangs.pokmontcgdeveloper.domain

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

    data class Success<out T>(
        val data: List<T>?,
        val networkFailure: Throwable?
    ): ListState<T>()

    data class Failure<out T>(val cause: Throwable): ListState<T>()

    companion object {

        fun <T> inProgress() =
            InProgress<T>()

        fun <T> success(
            data: List<T>? = null,
            networkFailure: Throwable? = null
        ) = Success(data, networkFailure)

        fun <T> failure(cause: Throwable) = Failure<T>(cause)

    }

}