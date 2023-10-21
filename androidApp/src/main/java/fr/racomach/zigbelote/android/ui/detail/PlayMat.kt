package fr.racomach.zigbelote.android.ui.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.racomach.zigbelote.android.R
import fr.racomach.zigbelote.android.theme.ZigBeloteColor
import fr.racomach.zigbelote.android.theme.ZigBeloteTheme

@Composable
fun PlayMat(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.(Modifier) -> Unit
) {
    Box(
        modifier = modifier
    ) {
        Image(
            painterResource(R.drawable.background_table),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(15.dp, 15.dp, 15.dp, 15.dp))
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp)
                .border(4.dp, ZigBeloteColor.green2, RoundedCornerShape(15.dp, 15.dp, 15.dp, 15.dp))
                .padding(8.dp)
        ) {
            content(Modifier)
        }

    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    device = Devices.NEXUS_7
)
@Composable
private fun PlayMatPreview() {
    ZigBeloteTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
        ) {
            PlayMat(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                Text(text = "Hello !")
            }
        }
    }
}