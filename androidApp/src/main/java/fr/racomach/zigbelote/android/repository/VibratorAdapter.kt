package fr.racomach.zigbelote.android.repository

import android.content.Context
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.core.content.ContextCompat.getSystemService
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VibratorAdapter @Inject constructor(@ApplicationContext val context: Context) {
    private val vibrator = getSystemService(context, Vibrator::class.java)

    fun vibrateNormal() {
        vibrator?.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE))
    }

    fun vibrateTop() {
        val pattern = longArrayOf(100, 100, 100)
        val effect = if (vibrator?.hasAmplitudeControl() == true) {
            val amplitudes = intArrayOf(50, 150, 255)
            VibrationEffect.createWaveform(pattern, amplitudes, -1)

        } else {
            VibrationEffect.createWaveform(pattern, -1)
        }
        vibrator?.vibrate(effect)
    }
}