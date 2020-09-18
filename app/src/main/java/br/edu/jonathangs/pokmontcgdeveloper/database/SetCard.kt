package br.edu.jonathangs.pokmontcgdeveloper.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.edu.jonathangs.pokmontcgdeveloper.domain.Card

@Entity(tableName = "set_cards")
data class SetCard (
    @PrimaryKey override val id: String,
    override val name: String,
    override val nationalPokedexNumber: Int,
    override val imageUrl: String,
    override val imageUrlHiRes: String,
    override val number: String,
    override val rarity: String?,
    override val series: String,
    override val set: String,
    override val setCode: String
): Card