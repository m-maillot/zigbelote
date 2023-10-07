package fr.racomach.zigbelote.android.manager

import com.google.mediapipe.tasks.vision.objectdetector.ObjectDetectorResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HandleDetection @Inject constructor() {

    private val _detectionFlow: MutableStateFlow<ObjectDetectorResult?> = MutableStateFlow(null)
    val detectFlow: StateFlow<ObjectDetectorResult?>
        get() = _detectionFlow

    fun onDetect(result: ObjectDetectorResult) {
        _detectionFlow.value = result
    }

}