package fr.racomach.zigbelote.android.usecase

import com.google.mediapipe.tasks.components.containers.Detection
import com.google.mediapipe.tasks.vision.objectdetector.ObjectDetectorResult
import fr.racomach.zigbelote.android.manager.HandleDetection
import fr.racomach.zigbelote.android.model.Card
import fr.racomach.zigbelote.android.repository.VibratorAdapter
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.flow.channelFlow
import javax.inject.Inject

class ComputeMathPoints @Inject constructor(
    private val handleDetection: HandleDetection,
    private val vibratorAdapter: VibratorAdapter,
) {

    private val delayMs = 500

    data class Input(
        val contract: Card.Suit,
    )

    sealed class Output {
        data class Idle(val cards: List<Card>, val points: Int) : Output()
        data class NewCard(
            val card: Card,
            val cardPoint: Int,
            val resume: CompletableDeferred<Unit>
        ) : Output()

        data object DetectingCard : Output()
    }

    suspend fun run(input: Input) = channelFlow {
        val detectedCards: MutableSet<Card> = mutableSetOf()
        var lastDetection: CardDetection? = null
        val (contract) = input
        send(Output.Idle(emptyList(), 0))
        handleDetection.detectFlow.collect { result ->
            if (result != null) {
                val bestResult =
                    result.toCards().filter { it.confidence > 0.7f }.maxByOrNull { it.confidence }

                if (bestResult == null) {
                    send(
                        Output.Idle(
                            detectedCards.toList(),
                            computePoints(detectedCards, contract)
                        )
                    )
                } else {
                    // Update the last detection because card has changed
                    val oldBestCard = lastDetection
                    if (oldBestCard == null) {
                        lastDetection = bestResult
                        // send(Output.NoNewCard(detectedCards.toList()))
                    } else if (oldBestCard.card != bestResult.card) {
                        lastDetection = bestResult
                        send(Output.DetectingCard)
                    } else if (oldBestCard.accepted == null) {
                        val previousDetection = lastDetection
                        if (previousDetection == null) {
                            send(Output.DetectingCard)
                        } else if (bestResult.card == previousDetection.card && (bestResult.timestampMs - previousDetection.timestampMs) > delayMs) {
                            if (!detectedCards.contains(bestResult.card)) {
                                val cardPoint = getPoint(bestResult.card, contract)
                                detectedCards.add(bestResult.card)
                                val resume = CompletableDeferred<Unit>()
                                send(Output.NewCard(bestResult.card, cardPoint, resume))
                                if (cardPoint > 11) {
                                    vibratorAdapter.vibrateTop()
                                } else {
                                    vibratorAdapter.vibrateNormal()
                                }
                                resume.await()
                                val points = computePoints(detectedCards, contract)
                                send(Output.Idle(detectedCards.toList(), points))
                            } else {
                                lastDetection = bestResult.copy(accepted = false)
                            }
                        } else if (bestResult.card == previousDetection.card) {
                            send(Output.DetectingCard)
                        }
                    }
                }
            }
        }

    }

    private fun computePoints(cards: Set<Card>, contract: Card.Suit) =
        cards.sumOf { getPoint(it, contract) }


    private fun getPoint(card: Card, contract: Card.Suit) =
        when (card.rank) {
            Card.Rank.AS -> 11
            Card.Rank.TEN -> 10
            Card.Rank.JACK -> if (card.suit == contract) 20 else 2
            Card.Rank.QUEEN -> 3
            Card.Rank.KING -> 4
            Card.Rank.NINE -> if (card.suit == contract) 14 else 0
            else -> 0
        }
}

private fun ObjectDetectorResult.toCards() = detections().flatMap { detection: Detection? ->
    detection?.categories()
        ?.map { CardDetection(it.categoryName().toCard(), it.score(), timestampMs()) } ?: emptySet()
}

private data class CardDetection(
    val card: Card,
    val confidence: Float,
    val timestampMs: Long,
    val accepted: Boolean? = null
)


@JvmName("categoryNameToCard")
private fun String.toCard() = Card(
    rank = when (substring(0, if (this.length == 2) 1 else 2)) {
        "7" -> Card.Rank.SEVEN
        "8" -> Card.Rank.EIGHT
        "9" -> Card.Rank.NINE
        "10" -> Card.Rank.TEN
        "J" -> Card.Rank.JACK
        "Q" -> Card.Rank.QUEEN
        "K" -> Card.Rank.KING
        else -> Card.Rank.AS
    },
    suit = when (this.last().toString()) {
        "H" -> Card.Suit.HEART
        "D" -> Card.Suit.DIAMOND
        "C" -> Card.Suit.CLUB
        else -> Card.Suit.SPADE
    }
)