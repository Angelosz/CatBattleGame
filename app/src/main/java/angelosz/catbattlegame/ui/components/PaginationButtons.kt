package angelosz.catbattlegame.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.unit.dp

@Composable
fun PaginationButtons(
    isNotFirstPage: Boolean,
    isNotLastPage: Boolean,
    onPreviousButtonClicked: () -> Unit,
    onNextButtonClicked: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Button(
            modifier = Modifier.padding(horizontal = 14.dp),
            onClick = onPreviousButtonClicked,
            enabled = isNotFirstPage,
        ) {
            Text(
                text = "<",
                style = MaterialTheme.typography.labelLarge.copy(
                    shadow = Shadow(
                        color = Color.Black,
                        offset = Offset(3f, 3f),
                        blurRadius = 1f
                    )
                ),
            )
        }
        Button(
            modifier = Modifier.padding(horizontal = 14.dp),
            onClick = onNextButtonClicked,
            enabled = isNotLastPage
        ) {
            Text(
                text = ">",
                style = MaterialTheme.typography.labelLarge.copy(
                    shadow = Shadow(
                        color = Color.Black,
                        offset = Offset(3f, 3f),
                        blurRadius = 1f
                    )
                ),
            )
        }
    }
}