package fr.racomach.zigbelote.android.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.racomach.zigbelote.android.extensions.conditional

@Composable
fun CardUi(modifier: Modifier = Modifier, cardModel: CardModel, withShadow: Boolean = false) {
    Image(
        modifier = modifier.conditional(withShadow) {
            shadow(8.dp, RoundedCornerShape(8.dp))
        }, painter = painterResource(id = cardModel.picture), contentDescription = cardModel.id
    )
}

@Preview
@Composable
private fun CardUiPreview() {
    CardUi(cardModel = cardQueenOfHeartPreview)
}