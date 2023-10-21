package fr.racomach.zigbelote.android.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.racomach.zigbelote.android.theme.ZigBeloteTheme
import fr.racomach.zigbelote.android.ui.camera.CameraScreenPlaceholderPreview
import fr.racomach.zigbelote.android.ui.detail.Details
import fr.racomach.zigbelote.android.ui.scoring.Score
import fr.racomach.zigbelote.android.viewModel.DetectCardUiState
import fr.racomach.zigbelote.android.viewModel.stateIdleForPreview

@Composable
fun DetectionScreen(
    modifier: Modifier = Modifier,
    state: DetectCardUiState,
    camera: @Composable BoxScope.(Modifier, DetectCardUiState) -> Unit,
) {
    Column(modifier = modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(0.1f)
                .padding(8.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            Score(modifier = Modifier.align(Alignment.Center), score = state.matchPoint)
        }
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .weight(0.65f)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            camera(
                Modifier
                    .width(maxWidth)
                    .height(maxHeight)
                    .align(Alignment.Center),
                state,
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp, top = 8.dp)
                .weight(0.25f),
            contentAlignment = Alignment.BottomCenter
        ) {
            Details(
                modifier = Modifier.fillMaxSize(),
                state = state
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
private fun DetectionScreenLightPreview() {
    ZigBeloteTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
        ) {
            DetectionScreen(
                state = stateIdleForPreview,
                camera = { modifier, state ->
                    CameraScreenPlaceholderPreview(
                        modifier = modifier,
                    )
                }
            )
        }
    }
}
