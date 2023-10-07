package fr.racomach.zigbelote.android.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import fr.racomach.zigbelote.android.camera.CameraScreenPlaceholderPreview
import fr.racomach.zigbelote.android.theme.ZigBeloteTheme
import fr.racomach.zigbelote.android.ui.detail.Details
import fr.racomach.zigbelote.android.viewModel.DetectCardUiState
import fr.racomach.zigbelote.android.viewModel.stateIdleForPreview

@Composable
fun DetectionScreen(
    modifier: Modifier = Modifier,
    state: DetectCardUiState,
    camera: @Composable BoxScope.(modifier: Modifier) -> Unit = { },
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
                .weight(0.5f)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            camera(
                modifier = Modifier
                    .width(min(maxHeight, maxWidth))
                    .height(min(maxHeight, maxWidth))
                    .align(Alignment.Center),
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.4f)
                .padding(8.dp),
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
private fun DetectionScreenPreview() {
    ZigBeloteTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            DetectionScreen(
                state = stateIdleForPreview,
                camera = {
                    CameraScreenPlaceholderPreview(
                        modifier = it
                    )
                }
            )
        }
    }
}