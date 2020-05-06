package br.edu.jonathangs.pokmontcgdeveloper.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import br.edu.jonathangs.pokmontcgdeveloper.database.Card
import br.edu.jonathangs.pokmontcgdeveloper.database.Set
import br.edu.jonathangs.pokmontcgdeveloper.domain.ListState.Companion.inProgress
import br.edu.jonathangs.pokmontcgdeveloper.domain.ListState.Companion.success
import br.edu.jonathangs.pokmontcgdeveloper.network.Endpoint
import br.edu.jonathangs.pokmontcgdeveloper.network.RequestStatus
import br.edu.jonathangs.pokmontcgdeveloper.network.data.Cards
import br.edu.jonathangs.pokmontcgdeveloper.network.data.Sets
import io.realm.ImportFlag
import io.realm.Realm
import io.realm.Realm.getDefaultInstance
import io.realm.RealmObject
import io.realm.kotlin.where
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class Repository(
    private val endpoint: Endpoint,
    private val database: Realm = getDefaultInstance()
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
        val sets = queryAll<Set>()
        emit(success(data = sets, networkFailure = networkFailure))
    }

    fun setCards(
        setCode: String,
        context: CoroutineContext
    ): LiveData<ListState<Card>> = liveData(context) {
        emit(inProgress())
        val networkFailure = when (val result = endpoint.setCards(setCode)) {
            is RequestStatus.Success -> {
                save(result.data)
                null
            }
            is RequestStatus.Failure -> result.cause
        }
        val cards = queryBySet(setCode)
        emit(success(data = cards, networkFailure = networkFailure))
    }

    fun allCards(
        pageSize: Int = 18,
        page: Int = 1,
        context: CoroutineContext
    ): LiveData<ListState<Card>> = liveData(context){
        emit(inProgress())
        val networkFailure = when (val result = endpoint.allCards(pageSize, page)) {
            is RequestStatus.Success -> {
                save(result.data)
                null
            }
            is RequestStatus.Failure -> result.cause
        }
        val limit = pageSize * page
        val cards = withContext(Dispatchers.Main) {
            database.where<Card>().limit(limit.toLong()).findAll()
        }
        emit(success(data = cards, networkFailure = networkFailure))
    }

    private suspend inline fun <reified T: RealmObject> queryAll() = withContext(Dispatchers.Main) {
        database.where<T>().findAll()
    }

    private suspend fun queryBySet(code: String) = withContext(Dispatchers.Main) {
        database.where<Card>().equalTo("setCode", code).findAll()
    }

    private suspend fun save(sets: Sets?) = withContext(Dispatchers.Main) {
        database.executeTransaction {
            sets?.sets?.forEach { set ->
                it.copyToRealmOrUpdate(set.toEntity(), ImportFlag.CHECK_SAME_VALUES_BEFORE_SET)
            }
        }
    }

    private suspend fun save(cards: Cards?) = withContext(Dispatchers.Main) {
        database.executeTransaction {
            cards?.cards?.forEach { card ->
                it.copyToRealmOrUpdate(card.toEntity(), ImportFlag.CHECK_SAME_VALUES_BEFORE_SET)
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

    private fun Cards.Card.toEntity() = Card(
        id = this.id,
        name = this.name,
        series = this.series,
        imageUrl = this.imageUrl,
        imageUrlHiRes = this.imageUrlHiRes,
        nationalPokedexNumber = this.nationalPokedexNumber,
        number = this.number,
        rarity = this.rarity,
        set = this.set,
        setCode = this.setCode
    )

}