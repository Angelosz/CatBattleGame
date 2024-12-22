package angelosz.catbattlegame.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import angelosz.catbattlegame.R

@Composable
fun BackButton(modifier: Modifier = Modifier, onBackPressed: () -> Unit) {
    Image(
        painter = painterResource(R.drawable.back_button_128),
        contentDescription = null,
        modifier = modifier
            .size(64.dp)
            .clickable(onClick = onBackPressed)
    )
}