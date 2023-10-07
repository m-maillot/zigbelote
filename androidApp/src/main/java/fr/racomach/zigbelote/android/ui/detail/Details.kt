package fr.racomach.zigbelote.android.ui.detail

import android.util.Log
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import fr.racomach.zigbelote.android.R
import fr.racomach.zigbelote.android.ui.CardModel
import fr.racomach.zigbelote.android.viewModel.DetectCardUiState

@Composable
fun Details(
    modifier: Modifier = Modifier,
    state: DetectCardUiState
) {
    val newCardVisibility = remember {
        MutableTransitionState(false)
    }
    val cardDetected = remember {
        mutableStateOf(CardModel("unknown", R.drawable.king_svg))
    }
    val detectCardVisibility = remember {
        MutableTransitionState(false)
    }
    val resume = remember {
        mutableStateOf({})
    }

    LaunchedEffect(state) {
        when (state) {
            is DetectCardUiState.DetectingCard -> {
                if (newCardVisibility.targetState) {
                    newCardVisibility.targetState = false
                }
                detectCardVisibility.targetState = true
            }

            is DetectCardUiState.Idle -> {
                if (newCardVisibility.targetState) {
                    newCardVisibility.targetState = false
                }
                detectCardVisibility.targetState = false
            }

            is DetectCardUiState.NewCard -> {
                detectCardVisibility.targetState = false
                cardDetected.value = state.card
                resume.value = state.resume
                newCardVisibility.targetState = true
            }
        }
    }

    Log.i("MMA-DEBUG-UI", "state : ${state::class.java.name}, current: ${newCardVisibility.currentState}, target: ${newCardVisibility.targetState}, idle: ${newCardVisibility.isIdle}")
    if (newCardVisibility.currentState && newCardVisibility.targetState && newCardVisibility.isIdle) {
        Log.i("MMA-DEBUG-UI", "Animation is done !")
        resume.value.invoke()
    }

    Box(modifier = modifier) {
        CardList(cards = state.cards)
        DetectedCard(visibleState = detectCardVisibility)
        CardFound(
            visibleState = newCardVisibility,
            card = cardDetected.value,
            cardPoint = (state as? DetectCardUiState.NewCard)?.cardPoint ?: 0
        )
    }
}