package br.edu.jonathangs.pokmontcgdeveloper.database.dao

import androidx.room.Query
import br.edu.jonathangs.pokmontcgdeveloper.database.model.Set

@androidx.room.Dao
interface SetsDao: Dao<Set>{
    @Query(value = "SELECT * FROM sets")
    fun queryAll(): List<Set>
}