package fr.racomach.zigbelote.android.ui.camera

import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import fr.racomach.zigbelote.android.R
import fr.racomach.zigbelote.android.theme.ZigBeloteTheme
import fr.racomach.zigbelote.android.ui.CardModel
import fr.racomach.zigbelote.android.viewModel.DetectCardUiState
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun CameraView(
    modifier: Modifier = Modifier,
    state: DetectCardUiState,
    camera: @Composable BoxScope.(Modifier) -> Unit
) {
    val newCardVisibility = remember {
        MutableTransitionState(false)
    }
    val newCardPointVisibility = remember {
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
                    newCardPointVisibility.targetState = false
                }
                detectCardVisibility.targetState = true
            }

            is DetectCardUiState.Idle -> {
                if (newCardVisibility.targetState) {
                    newCardVisibility.targetState = false
                    newCardPointVisibility.targetState = false
                }
                detectCardVisibility.targetState = false
            }

            is DetectCardUiState.NewCard -> {
                detectCardVisibility.targetState = false
                cardDetected.value = state.card
                resume.value = state.resume
                newCardVisibility.targetState = true
                newCardPointVisibility.targetState = true
            }
        }
    }

    LaunchedEffect(newCardVisibility.currentState) {
        if (newCardVisibility.currentState && newCardVisibility.targetState && newCardVisibility.isIdle) {
            delay(500.milliseconds)
            newCardVisibility.targetState = false
            newCardPointVisibility.targetState = false
        }
        if (!newCardVisibility.currentState && !newCardVisibility.targetState && newCardVisibility.isIdle) {
            resume.value.invoke()
        }
    }



    Box(modifier = modifier) {
        camera(Modifier.fillMaxSize())
        DetectedCard(visibleState = detectCardVisibility)
        CardFound(
            modifier = Modifier.fillMaxSize(),
            visibleState = newCardVisibility,
            card = cardDetected.value,
        )
        CardPoints(
            modifier = Modifier.fillMaxSize(),
            visibleState = newCardPointVisibility,
            cardPoint = (state as? DetectCardUiState.NewCard)?.cardPoint ?: 0
        )
    }
}

@Preview(
    device = Devices.NEXUS_7,
    showBackground = true,
    showSystemUi = true,
    name = "Ecran principal"
)
@Composable
private fun CameraScreenPreview() {
    val state = remember {
        mutableStateOf<DetectCardUiState>(DetectCardUiState.Idle())
    }

    ZigBeloteTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                CameraView(
                    state = state.value,
                    camera = {
                        CameraScreenPlaceholderPreview(
                            modifier = it,
                        )
                    }
                )

                Button(onClick = {
                    when (state.value) {
                        is DetectCardUiState.DetectingCard -> state.value =
                            DetectCardUiState.NewCard(
                                card = CardModel(
                                    id = "id1",
                                    picture = R.drawable.ace_spades_svg
                                ), cardPoint = 11, resume = {})

                        is DetectCardUiState.Idle -> state.value = DetectCardUiState.Idle()
                        is DetectCardUiState.NewCard -> state.value =
                            DetectCardUiState.DetectingCard()
                    }
                }) {
                    Text(text = "Change state")
                }
            }

        }
    }
}