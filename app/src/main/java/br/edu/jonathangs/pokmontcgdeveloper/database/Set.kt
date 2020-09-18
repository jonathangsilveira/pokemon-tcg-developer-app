package br.edu.jonathangs.pokmontcgdeveloper.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sets")
data class Set(
    @PrimaryKey var code: String,
    val ptcgoCode: String?,
    val name: String,
    val series: String,
    val totalCards: Int,
    val standardLegal: Boolean,
    val expandedLegal: Boolean,
    val releaseDate: String,
    val symbolUrl: String,
    val logoUrl: String
)