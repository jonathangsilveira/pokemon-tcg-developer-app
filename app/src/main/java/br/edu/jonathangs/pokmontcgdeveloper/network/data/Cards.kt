package br.edu.jonathangs.pokmontcgdeveloper.network.data


data class Cards(val cards: List<Card>) {
    data class Card(
        val id: String,
        val name: String,
        val nationalPokedexNumber: Int,
        val imageUrl: String,
        val imageUrlHiRes: String,
        val types: List<String>,
        val supertype: String,
        val subtype: String,
        val hp: String,
        val retreatCost: List<String>,
        val convertedRetreatCost: Int,
        val number: String,
        val artist: String,
        val rarity: String,
        val series: String,
        val `set`: String,
        val setCode: String,
        val attacks: List<Attack>,
        val weaknesses: List<Weaknesse>,
        val resistances: List<Resistance>,
        val text: List<String>,
        val evolvesFrom: String,
        val ability: Ability
    ) {
        data class Attack(
            val cost: List<String>,
            val name: String,
            val text: String,
            val damage: String,
            val convertedEnergyCost: Int
        )

        data class Weaknesse(
            val type: String,
            val value: String
        )

        data class Resistance(
            val type: String,
            val value: String
        )

        data class Ability(
            val name: String,
            val text: String,
            val type: String
        )
    }
}