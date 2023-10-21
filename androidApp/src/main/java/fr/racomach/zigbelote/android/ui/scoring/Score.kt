package fr.racomach.zigbelote.android.ui.scoring

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.racomach.zigbelote.android.theme.ZigBeloteColor
import fr.racomach.zigbelote.android.theme.ZigBeloteTheme

@Composable
fun Score(modifier: Modifier = Modifier, score: Int) {
    Row(modifier = modifier) {
        Text(
            modifier = Modifier.padding(end = 16.dp),
            fontSize = 24.sp,
            text = "Score : ",
            color = ZigBeloteColor.yellow,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineLarge.copy(
                shadow = Shadow(
                    color = Color.Black.copy(alpha = 0.7f),
                    offset = Offset(x = 4f, y = 5f),
                    blurRadius = 1f
                )
            ),
        )
        AnimatedContent(
            modifier = modifier,
            targetState = score,
            transitionSpec = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Up,
                    animationSpec = tween(durationMillis = 500)
                ) togetherWith
                        slideOutOfContainer(
                            towards = AnimatedContentTransitionScope.SlideDirection.Up,
                            animationSpec = tween(durationMillis = 500)
                        )
            },
            contentAlignment = Alignment.Center,
            label = "scoring"
        ) { targetScore ->
            Text(
                fontSize = 24.sp,
                text = "$targetScore",
                color = ZigBeloteColor.yellow,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineLarge.copy(
                    shadow = Shadow(
                        color = Color.Black.copy(alpha = 0.7f),
                        offset = Offset(x = 4f, y = 5f),
                        blurRadius = 1f
                    )
                ),
            )
        }
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
    ZigBeloteTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
        ) {
            Score(score = 48)
        }
    }
}