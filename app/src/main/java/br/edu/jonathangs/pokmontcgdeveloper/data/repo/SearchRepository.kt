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

typealias Strings = List<String>

class SearchRepository(
    private val webservice: CardsWebService,
    private val database: PokemonDatabase
) {

    fun flowCards(
        types: Strings,
        supertypes: Strings,
        subtypes: Strings,
        refresh: Boolean = false
    ): Flow<LoadState<Cards>> = flow {
        emitProgress<Cards>()
        val type = types.joinToString(separator = "|") { it.toLowerCase(Locale.ROOT) }
        val supertype = supertypes.joinToString(separator = "|") { it.toLowerCase(Locale.ROOT) }
        val subtype = subtypes.joinToString(separator = "|") { it.toLowerCase(Locale.ROOT) }
        val loadFromLocal = !refresh || !fetchCards(
            types = type,
            supertypes = supertype,
            subtypes = subtype
        )
        if (loadFromLocal) {
            val localCards = database.setCardDao()
                .flowCards(
                    types = types,
                    supertypes = supertypes,
                    subtypes = subtypes
                )
            emitSuccess(localCards)
        }
    }

    val flowTypes: Flow<LoadState<Strings>> = flow {
        emitProgress<Strings>()
        when (val result = safeCall { webservice.types() }) {
            is Result.Success -> emitSuccess(result.data?.types)
            is Result.Failure -> emitException<List<String>>(result.throwable)
        }
    }

    val flowSupertypes: Flow<LoadState<Strings>> = flow {
        emitProgress<Strings>()
        when (val result = safeCall { webservice.supertypes() }) {
            is Result.Success -> emitSuccess(result.data?.supertypes)
            is Result.Failure -> emitException<List<String>>(result.throwable)
        }
    }

    val flowSubtypes: Flow<LoadState<Strings>> = flow {
        emitProgress<Strings>()
        when (val result = safeCall { webservice.subtypes() }) {
            is Result.Success -> emitSuccess(result.data?.subtypes)
            is Result.Failure -> emitException<List<String>>(result.throwable)
        }
    }

    private suspend fun FlowCollector<LoadState<Cards>>.fetchCards(
        types: String? = null,
        supertypes: String? = null,
        subtypes: String? = null
    ): Boolean {
        val parameters = mutableMapOf<String, String>()
        if (!types.isNullOrEmpty())
            parameters["types"] = types
        if (!supertypes.isNullOrEmpty())
            parameters["supertype"] = supertypes
        if (!subtypes.isNullOrEmpty())
            parameters["subtype"] = subtypes
        return when (val result = safeCall { webservice.cards(parameters) }) {
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
            types = types,
            supertype = supertype,
            subtype = subtype
        )

}