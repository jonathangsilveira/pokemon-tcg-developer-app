package br.edu.jonathangs.pokmontcgdeveloper.data.local.dao

import androidx.room.Query
import br.edu.jonathangs.pokmontcgdeveloper.data.local.model.Set
import kotlinx.coroutines.flow.Flow

typealias Sets = List<Set>

@androidx.room.Dao
interface SetDao: BaseDao<Set> {
    @Query(value = "DELETE FROM sets")
    fun deleteAll()
    @Query(value = "SELECT * FROM sets")
    fun all(): Sets
    @Query(value = "SELECT * FROM sets LIMIT :limit OFFSET :offset")
    fun pagedSets(limit: Int, offset: Int): Sets
    @Query(value = "SELECT * FROM sets")
    fun flowAll(): Flow<Sets>
}