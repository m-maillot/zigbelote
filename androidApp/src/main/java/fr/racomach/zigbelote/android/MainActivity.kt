package fr.racomach.zigbelote.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import fr.racomach.zigbelote.android.ui.camera.CameraScreen
import fr.racomach.zigbelote.android.theme.ZigBeloteTheme
import fr.racomach.zigbelote.android.ui.DetectionScreen
import fr.racomach.zigbelote.android.ui.camera.CameraView
import fr.racomach.zigbelote.android.viewModel.DetectCardViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val viewModel: DetectCardViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.start()
        setContent {
            ZigBeloteTheme {
                val state = viewModel.uiState.collectAsState().value
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    DetectionScreen(
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