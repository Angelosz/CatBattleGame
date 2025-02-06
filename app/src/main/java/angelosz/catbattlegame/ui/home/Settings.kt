package angelosz.catbattlegame.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import angelosz.catbattlegame.R
import angelosz.catbattlegame.data.datastore.PlayerSettings

@Composable
fun SettingsScreen(
    settings: PlayerSettings,
    saveSettings:  (PlayerSettings) -> Unit,
) {
    var autoSelectTarget by remember { mutableStateOf(settings.autoTargetSelect) }
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = { saveSettings(
                PlayerSettings(
                    autoTargetSelect = autoSelectTarget
                )
            ) },
            modifier = Modifier
                .fillMaxSize(),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
        ) {}
        Card(
            modifier = Modifier
                .width(304.dp)
        ){
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 8.dp, top = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        text = stringResource(R.string.settings),
                        style = MaterialTheme.typography.headlineSmall,
                    )
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                ){
                    Column(){
                        Row(
                            modifier = Modifier.padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            Text(
                                modifier = Modifier.padding(4.dp).weight(1f),
                                text = stringResource(R.string.auto_target_selection)
                            )
                            Switch(
                                modifier = Modifier.padding(4.dp),
                                checked = autoSelectTarget,
                                onCheckedChange = {
                                    autoSelectTarget = it
                                }
                            )
                        }
                    }
                }

                Button(
                    modifier = Modifier
                        .padding(8.dp),
                    onClick = {
                        saveSettings(
                            PlayerSettings(
                                autoTargetSelect = autoSelectTarget
                            )
                        )
                    }
                ) {
                    Text(
                        modifier = Modifier.padding(4.dp),
                        text = stringResource(R.string.save)
                    )
                }
            }
        }
    }
}