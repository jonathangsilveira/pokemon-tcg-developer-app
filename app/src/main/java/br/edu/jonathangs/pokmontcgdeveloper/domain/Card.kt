package br.edu.jonathangs.pokmontcgdeveloper.domain

interface Card {
    val id: String
    val name: String
    val nationalPokedexNumber: Int
    val imageUrl: String
    val imageUrlHiRes: String
    val number: String
    val rarity: String?
    val series: String
    val `set`: String
    val setCode: String
}