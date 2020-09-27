package br.edu.jonathangs.pokmontcgdeveloper.domain

import androidx.room.withTransaction
import br.edu.jonathangs.pokmontcgdeveloper.data.Result
import br.edu.jonathangs.pokmontcgdeveloper.data.local.PokemonDatabase
import br.edu.jonathangs.pokmontcgdeveloper.data.local.dao.Sets
import br.edu.jonathangs.pokmontcgdeveloper.data.remote.WebService
import br.edu.jonathangs.pokmontcgdeveloper.data.remote.data.SetsResponse
import br.edu.jonathangs.pokmontcgdeveloper.data.remote.safeCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import br.edu.jonathangs.pokmontcgdeveloper.data.local.model.Set as SetModel

class Repository(
    private val webService: WebService,
    private val database: PokemonDatabase
) {

    fun flowSets(page: Int = 1): Flow<LoadState<Sets>> = flow {
        emit(LoadState.InProgress)
        when (val result = safeCall { webService.allSets() }) {
            is Result.Success -> saveSets(result.data)
            is Result.Failure -> emit(LoadState.Exception(result.throwable))
        }
        val sets: Flow<LoadState<Sets>> = flowAllSets().map { LoadState.Success(it) }
        emitAll(sets)
    }

    private fun flowAllSets() = database.setDao().flowAll()

    private suspend fun saveSets(response: SetsResponse?) {
        database.withTransaction {
            database.setDao().deleteAll()
            response ?: return@withTransaction
            val sets = response.sets.map { it.toModel }
            database.setDao().insertOrReplace(sets)
        }
    }

    private val SetsResponse.Set.toModel: SetModel
        get() = SetModel(
            code = this.code,
            ptcgoCode = this.ptcgoCode,
            name = this.name,
            series = this.series,
            totalCards = this.totalCards,
            standardLegal = this.standardLegal,
            expandedLegal = this.expandedLegal,
            releaseDate = this.releaseDate,
            symbolUrl = this.symbolUrl,
            logoUrl = this.logoUrl
        )

}