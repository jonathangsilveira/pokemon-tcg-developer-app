package br.edu.jonathangs.pokmontcgdeveloper.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import br.edu.jonathangs.pokmontcgdeveloper.database.dao.CardsDao
import br.edu.jonathangs.pokmontcgdeveloper.database.model.Card
import br.edu.jonathangs.pokmontcgdeveloper.database.model.CardPreview
import br.edu.jonathangs.pokmontcgdeveloper.network.Endpoint
import br.edu.jonathangs.pokmontcgdeveloper.network.RequestStatus
import br.edu.jonathangs.pokmontcgdeveloper.network.data.Cards
import kotlin.coroutines.CoroutineContext

class CardRepository(
    private val endpoint: Endpoint,
    private val dao: CardsDao
) {

    fun setCards(
        setCode: String,
        context: CoroutineContext
    ): LiveData<ListState<CardPreview>> = liveData(context) {
        emit(ListState.inProgress())
        val networkFailure = when (val result = endpoint.setCards(setCode)) {
            is RequestStatus.Success -> {
                save(result.data)
                null
            }
            is RequestStatus.Failure -> result.cause
        }
        val cards = dao.queryBySet(setCode)
        emit(ListState.success(data = cards, networkFailure = networkFailure))
    }

    fun allCards(
        pageSize: Int = 18,
        page: Int = 1,
        context: CoroutineContext
    ): LiveData<ListState<CardPreview>> = liveData(context){
        emit(ListState.inProgress())
        val networkFailure = when (val result = endpoint.allCards(pageSize, page)) {
            is RequestStatus.Success -> {
                save(result.data)
                null
            }
            is RequestStatus.Failure -> result.cause
        }
        val limit = pageSize * page
        val cards = dao.queryLimitedBy(limit)
        emit(ListState.success(data = cards, networkFailure = networkFailure))
    }

    private fun save(cards: Cards?) {
        cards?.cards?.map {
            it.toEntity()
        }?.let {
            dao.insertOrReplace(models = it)
        }
    }

    private fun Cards.Card.toEntity() =
        Card(
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