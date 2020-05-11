package br.edu.jonathangs.pokmontcgdeveloper.database.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy

interface Dao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrReplace(model: T)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrReplace(models: List<T>)
}