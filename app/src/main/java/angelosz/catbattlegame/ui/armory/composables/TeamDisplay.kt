package angelosz.catbattlegame.ui.armory.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import angelosz.catbattlegame.R
import angelosz.catbattlegame.ui.armory.data.ArmoryTeam
import angelosz.catbattlegame.ui.armory.data.SimpleArmoryCatData
import angelosz.catbattlegame.ui.components.CatCard

@Composable
fun TeamDisplay(
    modifier: Modifier = Modifier,
    team: ArmoryTeam,
    onTeamClicked: (Long) -> Unit = {},
    onCatCardClicked: (SimpleArmoryCatData) -> Unit = {},
    isSelected: Boolean = false,
    hasButton: Boolean = false,
    buttonText: String = "",
    onButtonClick: (Long) -> Unit = {},
    isNameEditable: Boolean = false,
    onNameEdited: (String) -> Unit = {},
    displayAsLongStrip: Boolean = false
){
    Box(modifier = modifier
        .background(if(isSelected) Color.LightGray else Color.White)
        .clickable(onClick = { onTeamClicked(team.teamId) })
    ) {
        Column(
            modifier = Modifier.padding(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            Row(
                verticalAlignment = Alignment.CenterVertically
            ){
                if(isNameEditable){
                    val keyboardController = LocalSoftwareKeyboardController.current
                    val focusManager = LocalFocusManager.current
                    var teamName by remember { mutableStateOf(team.teamName) }
                    TextField(
                        modifier = Modifier
                            .padding(horizontal = 8.dp),
                        value = teamName,
                        onValueChange = {
                            teamName = it
                            onNameEdited(teamName)
                        },
                        keyboardActions = KeyboardActions(
                            onDone = {
                                keyboardController?.hide()
                                focusManager.clearFocus()
                            }
                        ),
                        singleLine = true
                    )
                } else {
                    Text(
                        text = team.teamName,
                        style = MaterialTheme.typography.headlineSmall,
                    )
                }

                if(displayAsLongStrip){
                    TeamRowDisplay(team, onCatCardClicked)
                }

                if(hasButton){
                    Button(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        onClick = { onButtonClick(team.teamId) }
                    ){
                        Text(
                            text = buttonText,
                            modifier = Modifier
                                .padding(4.dp)
                        )
                    }
                }
            }
            if(!displayAsLongStrip){
                TeamRowDisplay(team, onCatCardClicked)
            }
        }
    }
}

@Composable
private fun TeamRowDisplay(
    team: ArmoryTeam,
    onCatCardClicked: (SimpleArmoryCatData) -> Unit,
    imageSize: Int = 76,
) {
    Row(
        modifier = Modifier
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(4) { index ->
            if (index >= team.cats.size) {
                CatCard(
                    id = 0,
                    image = R.drawable.battlechest_background_128,
                    onCardClicked = {},
                    imageSize = imageSize,
                    showBorder = true
                )
            } else {
                CatCard(
                    id = team.cats[index].id,
                    image = team.cats[index].image,
                    onCardClicked = { onCatCardClicked(team.cats[index]) },
                    imageSize = imageSize,
                    showBorder = true
                )
            }
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
                    SimpleArmoryCatData(1, R.drawable.kitten_swordman_300x300, 0, 0),
                    SimpleArmoryCatData(2, R.drawable.kitten_mage_300, 0, 0),
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
                    SimpleArmoryCatData(1, R.drawable.kitten_swordman_300x300, 0, 0),
                    SimpleArmoryCatData(2, R.drawable.kitten_mage_300, 0, 0),
                )
            ),
            hasButton = true,
            buttonText = "Save",
            isNameEditable = true
        )
    }
}