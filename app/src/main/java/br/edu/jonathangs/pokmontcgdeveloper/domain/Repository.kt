package br.edu.jonathangs.pokmontcgdeveloper.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import br.edu.jonathangs.pokmontcgdeveloper.database.Set
import br.edu.jonathangs.pokmontcgdeveloper.domain.ListState.Companion.inProgress
import br.edu.jonathangs.pokmontcgdeveloper.domain.ListState.Companion.success
import br.edu.jonathangs.pokmontcgdeveloper.network.Endpoint
import br.edu.jonathangs.pokmontcgdeveloper.network.RequestStatus
import br.edu.jonathangs.pokmontcgdeveloper.network.data.Sets
import io.realm.ImportFlag
import io.realm.Realm
import io.realm.Realm.getDefaultInstance
import io.realm.kotlin.where
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository(
    private val endpoint: Endpoint,
    private val database: Realm = getDefaultInstance()
) {

    fun allSets(): LiveData<ListState<Set>> = liveData(Dispatchers.IO) {
        emit(inProgress<Set>())
        val apiFailure = when (val result = endpoint.allSets()) {
            is RequestStatus.Success -> {
                save(result.data)
                null
            }
            is RequestStatus.Failure -> result
        }
        val sets = querySets()
        emit(success<Set, Sets>(data = sets, apiFailure = apiFailure))
    }

    private suspend fun querySets() = withContext(Dispatchers.Main) {
        database.where<Set>().findAll()
    }

    private suspend fun save(sets: Sets?) = withContext(Dispatchers.Main) {
        database.executeTransaction {
            sets?.sets?.forEach { set ->
                it.copyToRealmOrUpdate(set.toEntity(), ImportFlag.CHECK_SAME_VALUES_BEFORE_SET)
            }
        }
    }

    private fun Sets.Set.toEntity() = Set(
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