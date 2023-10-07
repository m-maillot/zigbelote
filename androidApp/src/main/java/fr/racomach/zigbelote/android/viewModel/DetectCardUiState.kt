package fr.racomach.zigbelote.android.viewModel

import fr.racomach.zigbelote.android.R
import fr.racomach.zigbelote.android.ui.CardModel

sealed class DetectCardUiState(
    open val cards: List<CardModel>,
    open val matchPoint: Int,
) {
    data class Idle(
        override val cards: List<CardModel> = emptyList(),
        override val matchPoint: Int = 0,
    ) : DetectCardUiState(cards, matchPoint)

    data class DetectingCard(
        override val cards: List<CardModel>,
        override val matchPoint: Int,
    ) : DetectCardUiState(cards, matchPoint)

    data class NewCard(
        override val cards: List<CardModel>,
        override val matchPoint: Int,
        val card: CardModel,
        val cardPoint: Int,
        val resume: () -> Unit,
    ) : DetectCardUiState(cards, matchPoint)
}

val stateIdleForPreview = DetectCardUiState.Idle(
    cards = listOf(
        CardModel(id = "id1", picture = R.drawable.ace_spades_svg),
        CardModel(id = "id2", picture = R.drawable.ace_clubs_svg),
        CardModel(id = "id3", picture = R.drawable.ace_hearts_svg),
        CardModel(id = "id4", picture = R.drawable.eight_spades_svg),
        CardModel(id = "id5", picture = R.drawable.jack_diamonds_svg),
        CardModel(id = "id6", picture = R.drawable.queen_clubs_svg),
        CardModel(id = "id7", picture = R.drawable.king_hearts_svg),
        CardModel(id = "id8", picture = R.drawable.seven_spades_svg),
        CardModel(id = "id8", picture = R.drawable.seven_hearts_svg),
        CardModel(id = "id8", picture = R.drawable.seven_diamonds_svg),
        CardModel(id = "id8", picture = R.drawable.eight_spades_svg),
        CardModel(id = "id8", picture = R.drawable.eight_diamonds_svg),
        CardModel(id = "id8", picture = R.drawable.jack_clubs_svg),
        CardModel(id = "id8", picture = R.drawable.queen_spades_svg),
        CardModel(id = "id8", picture = R.drawable.queen_hearts_svg),
        CardModel(id = "id8", picture = R.drawable.nine_diamonds_svg),
    ),
    matchPoint = 48,
)