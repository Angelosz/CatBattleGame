package angelosz.catbattlegame.ui.archives.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import angelosz.catbattlegame.R
import angelosz.catbattlegame.data.entities.Ability
import angelosz.catbattlegame.domain.enums.ArmorType
import angelosz.catbattlegame.ui.archives.data.ArchiveEnemyData
import angelosz.catbattlegame.ui.theme.CatBattleGameTheme

@Composable
fun ArchiveEnemyDetailsCard(
    modifier: Modifier = Modifier,
    enemy: ArchiveEnemyData,
    textSize: TextStyle = MaterialTheme.typography.bodyLarge,
    imageSize: Int = 300,
) {
    Card(
        modifier = modifier,
        shape = RectangleShape
    ) {
        Box(
            modifier = Modifier,
            contentAlignment = Alignment.Center,
        ) {
            ArchiveEnemyDetailsCardContent(
                enemy = enemy,
                imageSize = imageSize,
                textSize = textSize,
            )
        }
    }
}

@Composable
private fun ArchiveEnemyDetailsCardContent(
    enemy: ArchiveEnemyData,
    imageSize: Int = 300,
    textSize: TextStyle = MaterialTheme.typography.bodyLarge,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = enemy.name,
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
        )
        Image(
            painter = painterResource(enemy.image),
            contentDescription = enemy.name,
            modifier = Modifier
                .fillMaxWidth()
                .height(imageSize.dp),
        )
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = enemy.description,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                textAlign = TextAlign.Center
            )
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                modifier = Modifier.padding(4.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Column {
                        Text(
                            text = "Health: ${enemy.baseHealth}",
                            style = textSize,
                            modifier = Modifier.padding(4.dp),
                        )
                        Text(
                            text = "Defense: ${enemy.baseDefense}",
                            style = textSize,
                            modifier = Modifier.padding(4.dp)
                        )
                        Text(
                            text = "Armor: ${stringResource(enemy.armorType.res)}",
                            style = textSize,
                            modifier = Modifier.padding(4.dp)
                        )
                    }
                    Column {
                        Text(
                            text = "Attack: ${enemy.baseAttack}",
                            style = textSize,
                            modifier = Modifier.padding(4.dp)
                        )
                        Text(
                            text = "A. Speed: ${enemy.attackSpeed}s",
                            style = textSize,
                            modifier = Modifier.padding(4.dp)
                        )
                    }
                }
            }

            if (enemy.abilities.isNotEmpty()) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth()
                ) {
                    for (ability in enemy.abilities) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            Image(
                                painter = painterResource(ability.icon),
                                contentDescription = "${ability.name} ability icon",
                                modifier = Modifier.size(48.dp)
                            )
                            Text(
                                text = ability.name,
                                style = textSize,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ArchiveEnemyDetailsCardPreview(){
    CatBattleGameTheme {
        ArchiveEnemyDetailsCard(
            enemy = ArchiveEnemyData(
                id = 1,
                name = "Wool Ball",
                description = "Round and round it goes",
                image = R.drawable.enemy_wool_ball_300,
                baseHealth = 50,
                baseAttack = 15,
                baseDefense = 10,
                attackSpeed = 1.2f,
                armorType = ArmorType.LIGHT,
                abilities = listOf(
                    Ability(
                        id = 1,
                        name = "Quick Attack",
                        icon = R.drawable.ability_quick_attack_48,
                    ),
                    Ability(
                        id = 2,
                        name = "Quick Attack",
                        icon = R.drawable.ability_quick_attack_48,
                    )
                ),
            ),
        )
    }
}