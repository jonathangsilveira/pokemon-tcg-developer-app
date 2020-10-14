package br.edu.jonathangs.pokmontcgdeveloper.domain

enum class SearchParameters(val value: String) {
    TYPES("types"),
    NAME("name"),
    SUPERTYPE("supertype"),
    TEXT("text")
}