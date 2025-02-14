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
            text = stringResource(enemy.name),
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
        )
        Image(
            painter = painterResource(enemy.image),
            contentDescription = stringResource(enemy.name),
            modifier = Modifier
                .fillMaxWidth()
                .height(imageSize.dp),
        )
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(enemy.description),
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
                            text = stringResource(R.string.cat_desc_health),
                            style = textSize,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(4.dp),
                        )
                        Text(
                            text = "${enemy.baseHealth.toInt()}",
                            style = textSize,
                            modifier = Modifier.padding(4.dp),
                        )
                        Text(
                            text = stringResource(R.string.cat_desc_defense),
                            style = textSize,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(4.dp)
                        )
                        Text(
                            text = "${enemy.baseDefense.toInt()}",
                            style = textSize,
                            modifier = Modifier.padding(4.dp),
                        )
                        Text(
                            text = stringResource(R.string.cat_desc_armor),
                            style = textSize,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(4.dp)
                        )
                        Text(
                            text = stringResource(enemy.armorType.res),
                            style = textSize,
                            modifier = Modifier.padding(4.dp),
                        )
                    }
                    Column {
                        Text(
                            text = stringResource(R.string.cat_desc_attack),
                            style = textSize,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(4.dp)
                        )
                        Text(
                            text = "${enemy.baseAttack.toInt()}",
                            style = textSize,
                            modifier = Modifier.padding(4.dp),
                        )
                        Text(
                            text = stringResource(R.string.cat_desc_attack_speed),
                            style = textSize,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(4.dp)
                        )
                        Text(
                            text = "${enemy.attackSpeed}",
                            style = textSize,
                            modifier = Modifier.padding(4.dp),
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
                                contentDescription = stringResource(R.string.ability_icon_desc, ability.name),
                                modifier = Modifier.size(48.dp)
                            )
                            Text(
                                text = stringResource(ability.name),
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
                name = R.string.enemy_wool_ball,
                description = R.string.enemy_wool_ball_desc,
                image = R.drawable.enemy_wool_ball_300,
                baseHealth = 50f,
                baseAttack = 15f,
                baseDefense = 10f,
                attackSpeed = 1.2f,
                armorType = ArmorType.LIGHT,
                abilities = listOf(
                    Ability(
                        id = 1,
                        name = R.string.ability_quick_attack,
                        icon = R.drawable.ability_quick_attack_48,
                    ),
                    Ability(
                        id = 2,
                        name = R.string.ability_quick_attack,
                        icon = R.drawable.ability_quick_attack_48,
                    )
                ),
            ),
        )
    }
}