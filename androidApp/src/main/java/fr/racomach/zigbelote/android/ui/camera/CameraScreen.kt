package fr.racomach.zigbelote.android.ui.camera

import android.util.Log
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.LinearLayout
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.google.mediapipe.tasks.vision.core.RunningMode
import com.google.mediapipe.tasks.vision.objectdetector.ObjectDetectorResult
import fr.racomach.zigbelote.android.ObjectDetectorHelper
import fr.racomach.zigbelote.android.ObjectDetectorHelper.DetectorListener
import java.util.concurrent.Executors

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraScreen(
    modifier: Modifier = Modifier,
    onNewDetection: (result: ObjectDetectorResult) -> Unit
) {

    val cameraPermissionState: PermissionState =
        rememberPermissionState(android.Manifest.permission.CAMERA)

    LaunchedEffect(key1 = Unit) {
        if (!cameraPermissionState.status.isGranted && !cameraPermissionState.status.shouldShowRationale) {
            cameraPermissionState.launchPermissionRequest()
        }
    }

    if (cameraPermissionState.status.isGranted) {
        CameraPreview(modifier, onNewDetection)
    } else {
        // In this screen you should notify the user that the permission
        // is required and maybe offer a button to start another
        // camera perission request
        NoCameraPermissionScreen(
            cameraPermissionState = cameraPermissionState
        )
    }
}

@androidx.annotation.OptIn(ExperimentalGetImage::class)
@Composable
private fun CameraPreview(
    modifier: Modifier = Modifier,
    onNewDetection: (result: ObjectDetectorResult) -> Unit
) {

    val context = LocalContext.current
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    val cameraController: LifecycleCameraController =
        remember { LifecycleCameraController(context) }
    val executor = remember { Executors.newSingleThreadExecutor() }
    val objectDetectorHelper: ObjectDetectorHelper = remember {
        ObjectDetectorHelper(
            runningMode = RunningMode.LIVE_STREAM,
            context = context,
            objectDetectorListener = object : DetectorListener {
                override fun onError(error: String, errorCode: Int) {
                    Log.e("MMA-DEBUG", "Error : $error ($errorCode)")
                }

                override fun onResults(resultBundle: ObjectDetectorHelper.ResultBundle) {
                    val result = resultBundle.results.firstOrNull()
                    if (result != null) {
                        onNewDetection(result)
                    }
                }
            })
    }
    val imageAnalyzer: ImageAnalysis.Analyzer = remember {
        ImageAnalysis.Analyzer { imageProxy ->
            objectDetectorHelper.detectLivestreamFrame(imageProxy)
            imageProxy.close()
        }
    }
    KeepScreenOn()
    Scaffold(modifier = modifier) { innerPadding: PaddingValues ->
        AndroidView(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            factory = { context ->
                PreviewView(context).apply {
                    setBackgroundColor(Color.White.toArgb())
                    layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
                    scaleType = PreviewView.ScaleType.FILL_START
                    implementationMode = PreviewView.ImplementationMode.COMPATIBLE
                }.also { previewView ->
                    objectDetectorHelper.setupObjectDetector()
                    previewView.controller = cameraController
                    cameraController.bindToLifecycle(lifecycleOwner)
                    cameraController.cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
                    cameraController.setImageAnalysisAnalyzer(executor, imageAnalyzer)
                }
            },
            onRelease = {
                cameraController.unbind()
            }
        )
    }
}

@Composable
fun KeepScreenOn() = AndroidView({ View(it).apply { keepScreenOn = true } })

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun NoCameraPermissionScreen(cameraPermissionState: PermissionState) {
    Text(text = "Il manque la permission pour la caméra : ${cameraPermissionState.status}")
}