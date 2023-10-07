package fr.racomach.zigbelote.android.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.mediapipe.tasks.vision.objectdetector.ObjectDetectorResult
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.racomach.zigbelote.android.manager.HandleDetection
import fr.racomach.zigbelote.android.model.Card
import fr.racomach.zigbelote.android.ui.toModel
import fr.racomach.zigbelote.android.usecase.ComputeMathPoints
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetectCardViewModel @Inject constructor(
    private val handleDetection: HandleDetection,
    private val computeMathPoints: ComputeMathPoints,
) : ViewModel() {

    private val _uiState: MutableStateFlow<DetectCardUiState> = MutableStateFlow(DetectCardUiState.Idle())
    val uiState: StateFlow<DetectCardUiState>
        get() = _uiState

    fun onDetect(result: ObjectDetectorResult) {
        handleDetection.onDetect(result)
    }

    fun start() = viewModelScope.launch {
        computeMathPoints.run(ComputeMathPoints.Input(Card.Suit.HEART)).collect { result ->
            when (result) {
                is ComputeMathPoints.Output.NewCard -> {
                    _uiState.value = DetectCardUiState.NewCard(
                        cards = _uiState.value.cards,
                        matchPoint = _uiState.value.matchPoint,
                        card = result.card.toModel(),
                        cardPoint = result.cardPoint,
                        resume = {
                            result.resume.complete(Unit)
                        }
                    )
                }

                ComputeMathPoints.Output.DetectingCard -> {
                    _uiState.value = DetectCardUiState.DetectingCard(
                        cards = _uiState.value.cards,
                        matchPoint = _uiState.value.matchPoint,
                    )
                }

                is ComputeMathPoints.Output.Idle -> {
                    _uiState.value = DetectCardUiState.Idle(
                        cards = result.cards.map { it.toModel() },
                        matchPoint = result.points,
                    )
                }
            }
        }
    }
}