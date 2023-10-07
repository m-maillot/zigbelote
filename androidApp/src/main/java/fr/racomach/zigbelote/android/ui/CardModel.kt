package fr.racomach.zigbelote.android.ui

import androidx.annotation.DrawableRes
import fr.racomach.zigbelote.android.R
import fr.racomach.zigbelote.android.model.Card

data class CardModel(
    val id: String,
    @DrawableRes val picture: Int,
)

fun Card.toModel(): CardModel = CardModel(
    id = "${rank.name}_${suit.name}",
    picture = getPicture()
)

@DrawableRes
fun Card.getPicture(): Int = when (rank) {
    Card.Rank.AS -> when (suit) {
        Card.Suit.HEART -> R.drawable.ace_hearts_svg
        Card.Suit.DIAMOND -> R.drawable.ace_diamonds_svg
        Card.Suit.CLUB -> R.drawable.ace_clubs_svg
        Card.Suit.SPADE -> R.drawable.ace_spades_svg
    }

    Card.Rank.SEVEN -> when (suit) {
        Card.Suit.HEART -> R.drawable.seven_hearts_svg
        Card.Suit.DIAMOND -> R.drawable.seven_diamonds_svg
        Card.Suit.CLUB -> R.drawable.seven_clubs_svg
        Card.Suit.SPADE -> R.drawable.seven_spades_svg
    }

    Card.Rank.EIGHT -> when (suit) {
        Card.Suit.HEART -> R.drawable.eight_hearts_svg
        Card.Suit.DIAMOND -> R.drawable.eight_diamonds_svg
        Card.Suit.CLUB -> R.drawable.eight_clubs_svg
        Card.Suit.SPADE -> R.drawable.eight_spades_svg
    }

    Card.Rank.NINE -> when (suit) {
        Card.Suit.HEART -> R.drawable.nine_hearts_svg
        Card.Suit.DIAMOND -> R.drawable.nine_diamonds_svg
        Card.Suit.CLUB -> R.drawable.nine_clubs_svg
        Card.Suit.SPADE -> R.drawable.nine_spades_svg
    }

    Card.Rank.TEN -> when (suit) {
        Card.Suit.HEART -> R.drawable.ten_hearts_svg
        Card.Suit.DIAMOND -> R.drawable.ten_diamonds_svg
        Card.Suit.CLUB -> R.drawable.ten_clubs_svg
        Card.Suit.SPADE -> R.drawable.ten_spades_svg
    }

    Card.Rank.JACK -> when (suit) {
        Card.Suit.HEART -> R.drawable.jack_hearts_svg
        Card.Suit.DIAMOND -> R.drawable.jack_diamonds_svg
        Card.Suit.CLUB -> R.drawable.jack_clubs_svg
        Card.Suit.SPADE -> R.drawable.jack_spades_svg
    }

    Card.Rank.QUEEN -> when (suit) {
        Card.Suit.HEART -> R.drawable.queen_hearts_svg
        Card.Suit.DIAMOND -> R.drawable.queen_diamonds_svg
        Card.Suit.CLUB -> R.drawable.queen_clubs_svg
        Card.Suit.SPADE -> R.drawable.queen_spades_svg
    }

    Card.Rank.KING -> when (suit) {
        Card.Suit.HEART -> R.drawable.king_hearts_svg
        Card.Suit.DIAMOND -> R.drawable.king_diamonds_svg
        Card.Suit.CLUB -> R.drawable.king_clubs_svg
        Card.Suit.SPADE -> R.drawable.king_spades_svg
    }
}

data class SuitModel(val id: String, @DrawableRes val picture: Int, val value: Card.Suit)

fun Card.Suit.toModel(): SuitModel = SuitModel(
    id = name,
    picture = getPicture(),
    value = this,
)

@DrawableRes
fun Card.Suit.getPicture(): Int = when (this) {
    Card.Suit.HEART -> R.drawable.heart_svg
    Card.Suit.DIAMOND -> R.drawable.diamond_svg
    Card.Suit.CLUB -> R.drawable.club_svg
    Card.Suit.SPADE -> R.drawable.spade_svg
}

data class RankModel(val id: String, @DrawableRes val picture: Int, val value: Card.Rank)

fun Card.Rank.toModel(): RankModel = RankModel(
    id = name,
    picture = getPicture(),
    value = this,
)

@DrawableRes
fun Card.Rank.getPicture(): Int = when (this) {
    Card.Rank.AS -> R.drawable.ace_svg
    Card.Rank.SEVEN -> R.drawable.seven_svg
    Card.Rank.EIGHT -> R.drawable.eight_svg
    Card.Rank.NINE -> R.drawable.nine_svg
    Card.Rank.TEN -> R.drawable.ten_svg
    Card.Rank.JACK -> R.drawable.jack_svg
    Card.Rank.QUEEN -> R.drawable.queen_svg
    Card.Rank.KING -> R.drawable.king_svg
}

val cardQueenOfHeartPreview = Card(
    rank = Card.Rank.QUEEN,
    suit = Card.Suit.HEART,
).toModel()