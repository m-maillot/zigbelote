package fr.racomach.zigbelote.android.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.racomach.zigbelote.android.extensions.conditional
import fr.racomach.zigbelote.android.theme.colorWhite

@Composable
fun CardUi(modifier: Modifier = Modifier, cardModel: CardModel, withShadow: Boolean = false) {
    Image(
        modifier = modifier.conditional(withShadow) {
            shadow(2.dp, RoundedCornerShape(8.dp))
        }, painter = painterResource(id = cardModel.picture), contentDescription = cardModel.id
    )
}

@Preview(
    backgroundColor = colorWhite,
    showBackground = true,
    device = Devices.NEXUS_7,
    showSystemUi = true
)
@Composable
private fun CardUiPreview() {
    Column {
        CardUi(modifier = Modifier.padding(16.dp), cardModel = cardQueenOfHeartPreview)
        CardUi(
            modifier = Modifier.padding(16.dp),
            cardModel = cardQueenOfHeartPreview,
            withShadow = true
        )
    }

}