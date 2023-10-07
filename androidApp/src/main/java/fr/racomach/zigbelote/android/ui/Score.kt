package fr.racomach.zigbelote.android.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun Score(modifier: Modifier = Modifier, score: Int) {
    Box(modifier = modifier) {
        Text(
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            text = "$score point${if (score > 1) "s" else ""}"
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
private fun ScorePreview() {
    Score(score = 48)
}