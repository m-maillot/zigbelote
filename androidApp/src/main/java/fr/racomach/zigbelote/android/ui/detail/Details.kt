package fr.racomach.zigbelote.android.ui.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fr.racomach.zigbelote.android.viewModel.DetectCardUiState

@Composable
fun Details(
    modifier: Modifier = Modifier,
    state: DetectCardUiState
) {
    Box(modifier = modifier) {
        CardList(cards = state.cards)
    }
}