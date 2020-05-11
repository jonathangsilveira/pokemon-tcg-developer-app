package br.edu.jonathangs.pokmontcgdeveloper.database.dao

import androidx.room.Query
import br.edu.jonathangs.pokmontcgdeveloper.database.model.Card
import br.edu.jonathangs.pokmontcgdeveloper.database.model.CardPreview

@androidx.room.Dao
interface CardsDao: Dao<Card> {
    @Query(value = "SELECT id, imageUrl FROM cards WHERE setCode = :code")
    fun queryBySet(code: String): List<CardPreview>
    @Query(value = "SELECT * FROM cards")
    fun queryAll(): List<CardPreview>
    @Query(value = "SELECT id, imageUrl FROM cards LIMIT :limit")
    fun queryLimitedBy(limit: Int): List<CardPreview>
}