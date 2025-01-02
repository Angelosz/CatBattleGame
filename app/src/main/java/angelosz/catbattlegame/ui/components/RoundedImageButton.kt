package angelosz.catbattlegame.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import angelosz.catbattlegame.R

@Composable
fun RoundedImageButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    @DrawableRes innerImage: Int,
    @DrawableRes outerImage: Int = R.drawable.circular_button_128,
    outerImageSize: Int = 96,
    innerImageSize: Int = 80
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(outerImage),
            contentDescription = null,
            modifier = Modifier
                .size(outerImageSize.dp)
        )
        Image(
            painter = painterResource(innerImage),
            contentDescription = null,
            modifier = Modifier
                .size(innerImageSize.dp)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() },
                    onClick = onClick
                )
        )
    }
}