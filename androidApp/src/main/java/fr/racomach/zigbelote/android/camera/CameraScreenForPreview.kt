package fr.racomach.zigbelote.android.camera

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun CameraScreenPlaceholderPreview(modifier: Modifier = Modifier) {
    CameraPreview(modifier)
}

@Composable
private fun CameraPreview(modifier: Modifier = Modifier) {

    Scaffold(modifier = modifier) { innerPadding: PaddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.Cyan),
        )
    }
}