package angelosz.catbattlegame.ui.combat

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import angelosz.catbattlegame.R
import angelosz.catbattlegame.ui.components.CatCard
import angelosz.catbattlegame.ui.theme.CatBattleGameTheme
import angelosz.catbattlegame.utils.SoundManager

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
    val context = LocalContext.current
    // Rich medieval brown for selected team, dark steel/silver brown for unselected team
    val cardBgColor = if (markTeam) Color(0xFF2C1C13) else Color(0xFF383431)
    // Gold border for selected, Silver border for unselected
    val cardBorder = if (markTeam) BorderStroke(3.dp, Color(0xFFFFD700)) else BorderStroke(2.dp, Color(0xFFC0C0C0))

    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = cardBgColor
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = if (markTeam) 10.dp else 4.dp),
        border = cardBorder,
        onClick = {
            SoundManager.playButtonClick(context)
            onTeamCardClicked(teamData)
        },
        shape = RoundedCornerShape(16.dp),
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
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
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = if (markTeam) Color(0xFFFFD700) else Color(0xFFE0E0E0)
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
    val context = LocalContext.current
    Button(
        onClick = {
            SoundManager.playButtonClick(context)
            onDeleteTeamClicked(teamData)
        },
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFBA1A1A))
    ) {
        Text(
            text = "Delete",
            style = MaterialTheme.typography.labelMedium,
            color = Color.White,
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
                CatCard(
                    id = catData.catId,
                    image = catData.image,
                    imageSize = imageSize,
                    onCardClicked = onCatClicked
                )
            } else {
                Image(
                    painter = painterResource(R.drawable.cat_image_border),
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