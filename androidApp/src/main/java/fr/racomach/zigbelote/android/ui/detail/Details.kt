package fr.racomach.zigbelote.android.ui.detail

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fr.racomach.zigbelote.android.viewModel.DetectCardUiState

@Composable
fun Details(
    modifier: Modifier = Modifier,
    state: DetectCardUiState
) {
    CardList(modifier = modifier, cards = state.cards)
}