package br.edu.jonathangs.pokmontcgdeveloper.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cards")
data class Card (
    @PrimaryKey val id: String,
    val name: String,
    val nationalPokedexNumber: Int,
    val imageUrl: String,
    val imageUrlHiRes: String,
    val number: String,
    val rarity: String? = null,
    val series: String,
    val `set`: String,
    val setCode: String
)