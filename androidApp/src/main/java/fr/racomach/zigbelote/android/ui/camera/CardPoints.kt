package fr.racomach.zigbelote.android.ui.camera

import android.view.animation.AnticipateOvershootInterpolator
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import fr.racomach.zigbelote.android.extensions.toEasing
import fr.racomach.zigbelote.android.theme.ZigBeloteColor

@Composable
fun CardPoints(
    modifier: Modifier = Modifier,
    visibleState: MutableTransitionState<Boolean>,
    cardPoint: Int,
) {
    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        AnimatedVisibility(
            modifier = Modifier.align(Alignment.Center),
            visibleState = visibleState,
            enter = fadeIn(animationSpec = tween(600))
                    + scaleIn(
                animationSpec = tween(
                    1000,
                    easing = AnticipateOvershootInterpolator().toEasing()
                )
            ),
            exit = slideOutVertically(animationSpec = tween(500), targetOffsetY = {
                -(it + (it * 0.75)).toInt()
            }) + scaleOut(animationSpec = tween(500))
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = "+ $cardPoint",
                fontSize = 128.sp,
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
)
@Composable
private fun CardPointsPreview() {

    val visibleState = remember { MutableTransitionState(true) }

    Column(modifier = Modifier.fillMaxSize()) {
        CardPoints(
            modifier = Modifier
                .fillMaxHeight(0.3f)
                .fillMaxWidth(),
            visibleState = visibleState,
            cardPoint = 3,
        )
        Button(
            onClick = { visibleState.targetState = !visibleState.targetState },
            enabled = visibleState.currentState == visibleState.targetState
        ) {
            Text(text = "Visible")
        }
    }


}