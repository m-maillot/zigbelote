package fr.racomach.zigbelote.android.model

data class Card(val rank: Rank, val suit: Suit, val id: String = "${rank}_$suit") {
    enum class Rank(val showText: String) {
        AS("AS"), SEVEN("7"), EIGHT("8"), NINE("9"), TEN("10"), JACK("Valet"), QUEEN("Reine"), KING("Roi")
    }

    enum class Suit(val showText: String) {
        HEART("Coeur"), DIAMOND("Carreau"), CLUB("Trêfle"), SPADE("Pique")
    }

    override fun toString(): String {
        return "${rank.showText} ${suit.showText}"
    }
}