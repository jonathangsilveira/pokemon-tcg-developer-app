package br.edu.jonathangs.pokmontcgdeveloper.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import br.edu.jonathangs.pokmontcgdeveloper.database.dao.SetsDao
import br.edu.jonathangs.pokmontcgdeveloper.database.model.Set
import br.edu.jonathangs.pokmontcgdeveloper.domain.ListState.Companion.inProgress
import br.edu.jonathangs.pokmontcgdeveloper.domain.ListState.Companion.success
import br.edu.jonathangs.pokmontcgdeveloper.network.Endpoint
import br.edu.jonathangs.pokmontcgdeveloper.network.RequestStatus
import br.edu.jonathangs.pokmontcgdeveloper.network.data.Sets
import kotlin.coroutines.CoroutineContext

class SetRepository(
    private val endpoint: Endpoint,
    private val dao: SetsDao
) {

    fun allSets(context: CoroutineContext): LiveData<ListState<Set>> = liveData(context) {
        emit(inProgress())
        val networkFailure = when (val result = endpoint.allSets()) {
            is RequestStatus.Success -> {
                save(result.data)
                null
            }
            is RequestStatus.Failure -> result.cause
        }
        val sets = dao.queryAll()
        emit(success(data = sets, networkFailure = networkFailure))
    }

    private fun save(sets: Sets?) {
        sets?.sets?.map {
            it.toEntity()
        }?.let {
            dao.insertOrReplace(models = it)
        }
    }

    private fun Sets.Set.toEntity() =
        Set(
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