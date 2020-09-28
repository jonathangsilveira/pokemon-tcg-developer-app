package br.edu.jonathangs.pokmontcgdeveloper.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import br.edu.jonathangs.pokmontcgdeveloper.data.local.model.SetCard
import kotlinx.coroutines.flow.Flow

typealias SetCards = List<SetCard>

@Dao
interface SetCardDao: BaseDao<SetCard> {
    @Query("SELECT * FROM set_card WHERE setCode = :setCode")
    suspend fun cardsFromSet(setCode: String): SetCards
    @Query("SELECT * FROM set_card WHERE setCode = :setCode")
    fun flowCardsFromSet(setCode: String): Flow<SetCards>
    @Query("DELETE FROM set_card WHERE setCode = :setCode")
    fun deleteAllCardsFrom(setCode: String)
}