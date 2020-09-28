package br.edu.jonathangs.pokmontcgdeveloper.data.repo

import androidx.room.withTransaction
import br.edu.jonathangs.pokmontcgdeveloper.data.Result
import br.edu.jonathangs.pokmontcgdeveloper.data.local.PokemonDatabase
import br.edu.jonathangs.pokmontcgdeveloper.data.local.dao.SetCards
import br.edu.jonathangs.pokmontcgdeveloper.data.local.model.SetCard
import br.edu.jonathangs.pokmontcgdeveloper.data.remote.WebService
import br.edu.jonathangs.pokmontcgdeveloper.data.remote.data.CardsResponse
import br.edu.jonathangs.pokmontcgdeveloper.data.remote.safeCall
import br.edu.jonathangs.pokmontcgdeveloper.domain.LoadState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SetRepository(
    private val webService: WebService,
    private val database: PokemonDatabase
) {

    fun flowCards(setCode: String): Flow<LoadState<SetCards>> = flow {
        emit(LoadState.InProgress)
        when (val result = safeCall { webService.allCardsFrom(setCode) }) {
            is Result.Success -> save(setCode, result.data)
            is Result.Failure -> emit(LoadState.Exception(result.throwable))
        }
        val cards = database.setCardDao().cardsFromSet(setCode)
        emit(LoadState.Success(cards))
    }

    private suspend fun save(setCode: String, response: CardsResponse?) {
        val dao = database.setCardDao()
        database.withTransaction {
            dao.deleteAllCardsFrom(setCode)
            response ?: return@withTransaction
            val cards = response.cards.map { it.toModel }
            dao.insertOrReplace(cards)
        }
    }

    private val CardsResponse.Card.toModel: SetCard
        get() = SetCard(
            id,
            name,
            nationalPokedexNumber,
            imageUrl,
            imageUrlHiRes,
            number,
            rarity,
            series,
            set,
            setCode
        )

}