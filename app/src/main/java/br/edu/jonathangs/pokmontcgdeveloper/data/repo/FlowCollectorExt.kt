package br.edu.jonathangs.pokmontcgdeveloper.data.repo

import br.edu.jonathangs.pokmontcgdeveloper.domain.LoadState
import kotlinx.coroutines.flow.FlowCollector

internal suspend fun <T> FlowCollector<LoadState<T>>.emitSuccess(value: T?) {
    emit(LoadState.Success(value))
}

internal suspend fun <T> FlowCollector<LoadState<T>>.emitProgress() {
    emit(LoadState.InProgress)
}

internal suspend fun <T> FlowCollector<LoadState<T>>.emitException(cause: Throwable) {
    emit(LoadState.Exception(cause))
}