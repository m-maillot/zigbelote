package fr.racomach.zigbelote.android.ui.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.racomach.zigbelote.android.ui.CardModel
import fr.racomach.zigbelote.android.ui.CardUi
import fr.racomach.zigbelote.android.viewModel.stateIdleForPreview

@Composable
fun CardList(modifier: Modifier = Modifier, cards: List<CardModel>) {
    Box(modifier = modifier) {
        if (cards.isEmpty()) {
            Text(text = "Commencer à scanner vos plis")
        } else {
            LazyVerticalGrid(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(-16.dp),
                columns = GridCells.Adaptive(minSize = 48.dp)
            ) {
                items(cards) { card ->
                    CardUi(cardModel = card, withShadow = true)
                }
            }
        }
    }
}

@Preview(
    device = Devices.NEXUS_7,
    showBackground = true,
    showSystemUi = true,
    name = "Liste des cartes détectées"
)
@Composable
private fun CardListPreview() {
    Box(modifier = Modifier.fillMaxHeight(0.3f)) {
        CardList(
            cards = stateIdleForPreview.cards
        )
    }
}

