package angelosz.catbattlegame.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import angelosz.catbattlegame.R

@Composable
fun RoundedTextButton(
    modifier: Modifier = Modifier,
    text: String,
    size: Int = 32,
    onClick: () -> Unit = {},
    textStyle: TextStyle = MaterialTheme.typography.labelLarge
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.size(size.dp)
    ){
        Image(
            painter = painterResource(R.drawable.circular_button_128),
            contentDescription = null,
            modifier = Modifier
                .size(size.dp)
                .clickable(onClick = onClick)
        )
        Text(
            text = text,
            color = Color.White,
            style = textStyle.copy(
                shadow = Shadow(
                    color = Color.Black,
                    offset = Offset(3f, 3f),
                    blurRadius = 1f
                )
            ),
            fontWeight = FontWeight.Bold,
        )
    }
}