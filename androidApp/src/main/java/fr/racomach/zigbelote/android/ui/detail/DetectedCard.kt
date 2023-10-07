package fr.racomach.zigbelote.android.ui.detail

import android.view.animation.AnticipateOvershootInterpolator
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.racomach.zigbelote.android.extensions.toEasing

@Composable
fun DetectedCard(modifier: Modifier = Modifier, visibleState: MutableTransitionState<Boolean>) {
    Box(modifier = modifier.fillMaxSize()) {
        AnimatedVisibility(
            modifier = Modifier.align(Alignment.Center),
            visibleState = visibleState,
            enter = fadeIn(animationSpec = tween(100))
                    + scaleIn(
                animationSpec = tween(
                    200,
                    easing = AnticipateOvershootInterpolator().toEasing()
                )
            ),
            exit = fadeOut(animationSpec = tween(100))
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .width(64.dp)
                    .align(Alignment.Center),
                color = MaterialTheme.colors.primary,
                backgroundColor = MaterialTheme.colors.onPrimary,
            )
        }
    }
}

@Preview(
    device = Devices.NEXUS_7,
    showSystemUi = true,
    showBackground = true,
)
@Composable
fun DetectedCardPreview() {
    val visibleState = remember { MutableTransitionState(true) }

    Column(modifier = Modifier.fillMaxSize()) {
        DetectedCard(
            modifier = Modifier
                .fillMaxHeight(0.3f)
                .fillMaxWidth(),
            visibleState = visibleState,
        )
        Button(
            onClick = { visibleState.targetState = !visibleState.targetState },
            enabled = visibleState.currentState == visibleState.targetState
        ) {
            Text(text = "Visible")
        }
    }
}