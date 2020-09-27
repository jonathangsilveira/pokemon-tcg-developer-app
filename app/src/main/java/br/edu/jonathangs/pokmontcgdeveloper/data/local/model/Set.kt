package br.edu.jonathangs.pokmontcgdeveloper.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sets")
data class Set(
    @PrimaryKey val code: String,
    val ptcgoCode: String? = null,
    val name: String,
    val series: String,
    val totalCards: Int = 0,
    val standardLegal: Boolean,
    val expandedLegal: Boolean,
    val releaseDate: String,
    val symbolUrl: String,
    val logoUrl: String
)