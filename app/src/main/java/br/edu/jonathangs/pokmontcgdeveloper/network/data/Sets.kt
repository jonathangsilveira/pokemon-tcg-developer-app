package br.edu.jonathangs.pokmontcgdeveloper.network.data


data class Sets(val sets: List<Set>) {
    data class Set(
        val code: String,
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
}