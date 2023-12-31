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
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.racomach.zigbelote.android.extensions.toEasing
import fr.racomach.zigbelote.android.ui.CardModel
import fr.racomach.zigbelote.android.ui.CardUi
import fr.racomach.zigbelote.android.ui.cardQueenOfHeartPreview

@Composable
fun CardFound(
    modifier: Modifier = Modifier,
    visibleState: MutableTransitionState<Boolean>,
    card: CardModel,
) {
    Box(
        modifier = modifier
            .fillMaxHeight()
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
                (it * 0.7).toInt()
            }) + scaleOut(animationSpec = tween(500))
        ) {

            CardUi(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(32.dp),
                cardModel = card
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
private fun CardFoundPreview() {

    val visibleState = remember { MutableTransitionState(true) }

    Column(modifier = Modifier.fillMaxSize()) {
        CardFound(
            modifier = Modifier
                .fillMaxHeight(0.3f)
                .fillMaxWidth(),
            visibleState = visibleState,
            card = cardQueenOfHeartPreview,
        )
        Button(
            onClick = { visibleState.targetState = !visibleState.targetState },
            enabled = visibleState.currentState == visibleState.targetState
        ) {
            Text(text = "Visible")
        }
    }


}