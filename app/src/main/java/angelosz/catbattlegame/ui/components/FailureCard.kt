package angelosz.catbattlegame.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FailureCard(
    onBackPressed: () -> Unit,
    onReloadPressed: () -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier.padding(16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 16.dp, horizontal = 32.dp),
                    text = "There was an error... :("
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.padding(16.dp)
                ) {
                    Button(
                        onClick = onBackPressed,
                        modifier = Modifier.padding(4.dp)
                    ) {
                        Text(
                            modifier = Modifier.padding(8.dp),
                            text = "Go back"
                        )
                    }
                    Button(
                        onClick = onReloadPressed,
                        modifier = Modifier.padding(4.dp)
                    ) {
                        Text(
                            modifier = Modifier.padding(8.dp),
                            text = "Reload Team Builder"
                        )
                    }
                }
            }
        }
    }
}