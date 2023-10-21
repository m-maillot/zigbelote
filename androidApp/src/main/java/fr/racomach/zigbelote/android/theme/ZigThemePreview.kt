package fr.racomach.zigbelote.android.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
private fun ZigThemeScreen() {
    ZigBeloteTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Hello !")

                Button(onClick = { }) {
                    Text(text = "Switch light/dark mode !")
                }
                CircularProgressIndicator(
                    modifier = Modifier
                        .width(64.dp)
                )
            }
        }
    }
}

@Preview(
    device = Devices.NEXUS_7,
    showSystemUi = true,
    showBackground = true,
)
@Composable
fun ZigThemePreview() {
    ZigThemeScreen()
}
