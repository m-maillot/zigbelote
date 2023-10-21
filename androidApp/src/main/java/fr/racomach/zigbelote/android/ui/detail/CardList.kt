package fr.racomach.zigbelote.android.ui.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.racomach.zigbelote.android.theme.ZigBeloteTheme
import fr.racomach.zigbelote.android.ui.CardModel
import fr.racomach.zigbelote.android.ui.CardUi
import fr.racomach.zigbelote.android.viewModel.stateIdleForPreview

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CardList(modifier: Modifier = Modifier, cards: List<CardModel>) {
    PlayMat(modifier = modifier.fillMaxSize()) {
        if (cards.isEmpty()) {
            Text(text = "Commencer à scanner vos plis")
        } else {
            FlowRow(
                modifier = Modifier,
                horizontalArrangement = Arrangement.spacedBy(-24.dp),
            ) {
                cards.map { card ->
                    CardUi(
                        modifier = Modifier.size(48.dp),
                        cardModel = card,
                        withShadow = true
                    )
                }
            }
            /*
            LazyVerticalGrid(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(-24.dp),
                columns = GridCells.Adaptive(minSize = 48.dp),
                contentPadding = PaddingValues(horizontal = 4.dp, vertical = 4.dp),
            ) {
                items(cards) { card ->
                    CardUi(cardModel = card, withShadow = true)
                }
            }
            */
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
    ZigBeloteTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
        ) {
            Box(modifier = Modifier) {
                CardList(
                    cards = stateIdleForPreview.cards
                )
            }
        }
    }
}

