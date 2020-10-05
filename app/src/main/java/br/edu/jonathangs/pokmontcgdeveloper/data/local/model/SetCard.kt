package br.edu.jonathangs.pokmontcgdeveloper.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "set_card")
class SetCard(
    @PrimaryKey override val id: String,
    override val name: String,
    override val nationalPokedexNumber: Int,
    override val imageUrl: String,
    override val imageUrlHiRes: String,
    override val number: String,
    override val rarity: String? = null,
    override val series: String,
    override val `set`: String,
    override val setCode: String,
    override val types: List<String>
): Card()