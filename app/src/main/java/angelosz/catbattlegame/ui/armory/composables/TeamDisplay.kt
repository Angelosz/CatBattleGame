package angelosz.catbattlegame.ui.armory.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import angelosz.catbattlegame.R
import angelosz.catbattlegame.ui.armory.data.ArmoryTeam
import angelosz.catbattlegame.ui.armory.data.SimpleArmoryCatData

@Composable
fun TeamDisplay(
    modifier: Modifier = Modifier,
    team: ArmoryTeam,
    onTeamClicked: () -> Unit = {},
    isSelected: Boolean = false,
    hasButton: Boolean = false,
    buttonText: String = "",
    onButtonClick: () -> Unit = {},
    isNameEditable: Boolean = false,
    onNameEdited: (String) -> Unit = {}
){
    Box(modifier = modifier
        .background(if(isSelected) Color.DarkGray else Color.LightGray)
        .clickable(onClick = onTeamClicked)){
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                if(isNameEditable){
                    var teamName by remember { mutableStateOf(team.teamName) }
                    TextField(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 8.dp),
                        value = teamName,
                        onValueChange = {
                            teamName = it
                        },
                        keyboardActions = KeyboardActions(
                            onDone = { onNameEdited(teamName) }
                        )
                    )
                } else {
                    Text(
                        text = team.teamName,
                        style = MaterialTheme.typography.headlineSmall,
                    )
                }
                if(hasButton){
                    Button(onClick = onButtonClick){
                        Text(
                            text = buttonText,
                            modifier = Modifier.padding(4.dp)
                        )
                    }
                }
            }
            ArmoryCatGrid(
                cats = team.cats,
                onCatCardClicked = {},
                pageLimit = 4,
                imageSize = 76
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ArmoryTeamDisplayPreview(){
    Box(modifier = Modifier.padding(16.dp)) {
        TeamDisplay(
            team = ArmoryTeam(
                teamId = 1,
                teamName = "Team Name",
                cats = listOf(
                    SimpleArmoryCatData(1, R.drawable.kitten_swordman_300x300, 1, 20),
                    SimpleArmoryCatData(2, R.drawable.kitten_mage_300, 2, 30),
                )
            )
        )
    }
}

@Preview
@Composable
fun ArmoryTeamDisplayWithEditableNamePreview(){
    Box(modifier = Modifier.padding(16.dp)) {
        TeamDisplay(
            team = ArmoryTeam(
                teamId = 1,
                teamName = "Team Name",
                cats = listOf(
                    SimpleArmoryCatData(1, R.drawable.kitten_swordman_300x300, 1, 20),
                    SimpleArmoryCatData(2, R.drawable.kitten_mage_300, 2, 30),
                )
            ),
            hasButton = true,
            buttonText = "Save",
            isNameEditable = true
        )
    }
}