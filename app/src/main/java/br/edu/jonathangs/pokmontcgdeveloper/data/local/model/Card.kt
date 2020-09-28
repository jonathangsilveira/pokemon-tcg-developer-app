package br.edu.jonathangs.pokmontcgdeveloper.data.local.model

abstract class Card {
    abstract val id: String
    abstract val name: String
    abstract val nationalPokedexNumber: Int
    abstract val imageUrl: String
    abstract val imageUrlHiRes: String
    abstract val number: String
    abstract val rarity: String?
    abstract val series: String
    abstract val `set`: String
    abstract val setCode: String
}