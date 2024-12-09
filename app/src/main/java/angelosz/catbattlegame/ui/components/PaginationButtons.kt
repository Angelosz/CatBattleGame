package angelosz.catbattlegame.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun PaginationButtons(
    isNotFirstPage: Boolean,
    isNotLastPage: Boolean,
    onPreviousButtonClicked: () -> Unit,
    onNextButtonClicked: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Button(
            onClick = onPreviousButtonClicked,
            enabled = isNotFirstPage,
        ) {
            Text(
                text = "<",
                style = MaterialTheme.typography.labelMedium,
            )
        }
        Button(
            onClick = onNextButtonClicked,
            enabled = isNotLastPage
        ) {
            Text(
                text = ">",
                style = MaterialTheme.typography.labelMedium,
            )
        }
    }
}