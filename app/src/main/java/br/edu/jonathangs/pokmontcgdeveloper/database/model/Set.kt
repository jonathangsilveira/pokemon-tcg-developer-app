package br.edu.jonathangs.pokmontcgdeveloper.database.model

class Set(
    var code: String = "",
    var ptcgoCode: String? = null,
    var name: String = "",
    var series: String = "",
    var totalCards: Int = 0,
    var standardLegal: Boolean = false,
    var expandedLegal: Boolean = false,
    var releaseDate: String = "",
    var symbolUrl: String = "",
    var logoUrl: String = ""
)