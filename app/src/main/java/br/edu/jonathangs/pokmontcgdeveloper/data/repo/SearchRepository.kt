package br.edu.jonathangs.pokmontcgdeveloper.data.repo

import br.edu.jonathangs.pokmontcgdeveloper.data.Result
import br.edu.jonathangs.pokmontcgdeveloper.data.local.PokemonDatabase
import br.edu.jonathangs.pokmontcgdeveloper.data.local.model.Card
import br.edu.jonathangs.pokmontcgdeveloper.data.local.model.SetCard
import br.edu.jonathangs.pokmontcgdeveloper.data.remote.CardsWebService
import br.edu.jonathangs.pokmontcgdeveloper.data.remote.data.CardsResponse
import br.edu.jonathangs.pokmontcgdeveloper.data.remote.safeCall
import br.edu.jonathangs.pokmontcgdeveloper.domain.LoadState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import java.util.*

typealias Cards = List<Card>

class SearchRepository(
    private val webservice: CardsWebService,
    private val database: PokemonDatabase
) {

    fun flowCards(types: List<String>, refresh: Boolean = false): Flow<LoadState<Cards>> = flow {
        emit(LoadState.InProgress)
        val type = types.joinToString { it.toLowerCase(Locale.ROOT) }
        val loadFromLocal = !refresh || !fetchCards(type)
        if (loadFromLocal) {
            val localCards = database.setCardDao()
                .flowCards(types = types.map { it.toLowerCase(Locale.ROOT) })
            emitSuccess(localCards)
        }
    }

    val flowTypes: Flow<LoadState<List<String>>> = flow {
        emit(LoadState.InProgress)
        when (val result = safeCall { webservice.types() }) {
            is Result.Success -> emitSuccess(result.data?.types)
            is Result.Failure -> emitException<List<String>>(result.throwable)
        }
    }

    private suspend fun FlowCollector<LoadState<Cards>>.fetchCards(types: String): Boolean {
        return when (val result =
            safeCall { webservice.cards(parameters = mapOf("types" to types)) }) {
            is Result.Success -> {
                val cards = result.data?.cards?.map { it.toModel }
                emitSuccess(cards)
                true
            }
            is Result.Failure -> {
                emitException(result.throwable)
                false
            }
        }
    }

    private val CardsResponse.Card.toModel: Card
        get() = SetCard(
            id = id,
            name = name,
            nationalPokedexNumber = nationalPokedexNumber,
            imageUrl = imageUrl,
            imageUrlHiRes = imageUrlHiRes,
            number = number,
            rarity = rarity,
            series = series,
            set = set,
            setCode = setCode,
            types = types
        )

}