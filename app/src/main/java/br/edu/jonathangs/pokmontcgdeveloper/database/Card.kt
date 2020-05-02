package br.edu.jonathangs.pokmontcgdeveloper.database

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Card (
    @PrimaryKey var id: String = "",
    var name: String = "",
    var nationalPokedexNumber: Int = -1,
    var imageUrl: String = "",
    var imageUrlHiRes: String = "",
    var number: String = "",
    var rarity: String? = null,
    var series: String = "",
    var `set`: String = "",
    var setCode: String = ""
): RealmObject()