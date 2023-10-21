package fr.racomach.zigbelote.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dagger.hilt.android.AndroidEntryPoint
import fr.racomach.zigbelote.android.theme.ZigBeloteTheme
import fr.racomach.zigbelote.android.ui.DetectionScreen
import fr.racomach.zigbelote.android.ui.camera.CameraScreen
import fr.racomach.zigbelote.android.ui.camera.CameraView
import fr.racomach.zigbelote.android.viewModel.DetectCardViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val viewModel: DetectCardViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.start()

        // Turn off the decor fitting system windows, which allows us to handle insets,
        // including IME animations, and go edge-to-edge
        // This also sets up the initial system bar style based on the platform theme
        enableEdgeToEdge()

        setContent {
            ZigBeloteTheme {
                val state = viewModel.uiState.collectAsState().value
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    DetectionScreen(
                        modifier = Modifier.padding(top = 32.dp),
                        state = state,
                        camera = { modifier, state ->
                            CameraView(modifier = modifier, state = state) {
                                CameraScreen(
                                    modifier = modifier, onNewDetection = viewModel::onDetect
                                )
                            }
                        }
                    )
                }
            }
        }
    }
}