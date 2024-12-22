package angelosz.catbattlegame.ui.teambuilder

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import angelosz.catbattlegame.R
import angelosz.catbattlegame.ui.components.SmallImageCard
import angelosz.catbattlegame.ui.theme.CatBattleGameTheme

@Composable
fun TeamPanel(
    teamData: TeamData,
    modifier: Modifier = Modifier,
    imageSize: Int = 64,
    spacedBy: Int = 8,
    onTeamCardClicked: (TeamData) -> Unit = {},
    hasDeleteButton: Boolean = false,
    onDeleteTeamClicked: (TeamData) -> Unit = {},
    onCatClicked: (Int) -> Unit,
    markTeam: Boolean = false,
    isNameEditable: Boolean = false,
    onNameChanged: (String) -> Unit = {}
){
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = if(markTeam) Color.LightGray else Color.White
        ),
        onClick = { onTeamCardClicked(teamData) },
        shape = RectangleShape,
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(8.dp)
            ) {
                if(isNameEditable){
                    var teamName by remember { mutableStateOf(teamData.teamName) }
                    TextField(
                        value = teamName,
                        onValueChange = {
                            teamName = it
                            onNameChanged(it)
                        },
                        singleLine = true,
                    )
                } else {
                    Text(
                        text = teamData.teamName,
                        style = MaterialTheme.typography.headlineSmall,
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                if(hasDeleteButton){
                    DeleteTeamButton(onDeleteTeamClicked, teamData)
                }
            }
            TeamRow(teamData, imageSize, spacedBy, onCatClicked)
        }
    }
}

@Composable
private fun DeleteTeamButton(
    onDeleteTeamClicked: (TeamData) -> Unit,
    teamData: TeamData,
) {
    Button(
        onClick = { onDeleteTeamClicked(teamData) },
    ) {
        Text(
            text = "Delete Team",
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(2.dp)
        )
    }
}

@Composable
private fun TeamRow(
    teamData: TeamData,
    imageSize: Int,
    itemsSpacedBy: Int,
    onCatClicked: (Int) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(itemsSpacedBy.dp),
        modifier = Modifier.padding(8.dp),
    ) {
        repeat(4) { index ->
            val catData = teamData.cats.getOrNull(index)
            if (catData != null) {
                SmallImageCard(
                    id = catData.catId,
                    image = catData.image,
                    imageSize = imageSize,
                    onCardClicked = onCatClicked
                )
            } else {
                Image(
                    painter = painterResource(R.drawable.battlechest_background_128),
                    contentDescription = null,
                    modifier = Modifier
                        .size(imageSize.dp),
                    contentScale = ContentScale.FillBounds
                )
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Preview_TeamPanel(){
    CatBattleGameTheme {
        TeamPanel(
            teamData = TeamData(
                teamId = 0,
                teamName = "Create New Team",
                cats = listOf(
                    BasicCatData(
                        catId = 0,
                        image = R.drawable.kitten_swordman_300x300
                    )
                )
            ),
            modifier = Modifier.padding(16.dp).width(360.dp),
            imageSize = 80,
            spacedBy = 8,
            onTeamCardClicked = { },
            onDeleteTeamClicked = { },
            onCatClicked = {},
            isNameEditable = true,
        )
    }
}