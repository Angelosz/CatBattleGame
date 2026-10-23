package angelosz.catbattlegame.ui.armory.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
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
import angelosz.catbattlegame.domain.enums.CatRarity
import angelosz.catbattlegame.domain.enums.CatRole
import angelosz.catbattlegame.ui.armory.data.DetailedArmoryCatData
import angelosz.catbattlegame.ui.components.ExperienceBar
import angelosz.catbattlegame.ui.theme.CatBattleGameTheme
import angelosz.catbattlegame.utils.SoundManager

@Composable
fun ArmoryCatDetailsCard(
    modifier: Modifier = Modifier,
    cat: DetailedArmoryCatData,
    textSize: TextStyle = MaterialTheme.typography.bodyLarge,
    imageSize: Int = 192,
    onTrainWithCrystals: (Int, Int) -> Unit = { _, _ -> }
) {
    Card(
        modifier = modifier,
        shape = RectangleShape
    ) {
        Box(
            modifier = Modifier,
            contentAlignment = Alignment.Center,
        ) {
            ArmoryCatDetailsCatContent(
                cat = cat,
                imageSize = imageSize,
                textSize = textSize,
                onTrainWithCrystals = onTrainWithCrystals
            )
        }
    }
}

@Composable
private fun ArmoryCatDetailsCatContent(
    cat: DetailedArmoryCatData,
    imageSize: Int = 192,
    textSize: TextStyle = MaterialTheme.typography.bodyLarge,
    onTrainWithCrystals: (Int, Int) -> Unit = { _, _ -> }
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = stringResource(cat.title, stringResource(cat.name)),
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
        )
        Image(
            painter = painterResource(cat.image),
            contentDescription = stringResource(cat.name),
            modifier = Modifier
                .fillMaxWidth()
                .height(imageSize.dp),
        )
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            ExperienceBar(
                modifier = Modifier.padding(horizontal = 32.dp),
                level = cat.level,
                experience = cat.experience,
                showText = true
            )
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                onClick = {
                    SoundManager.playButtonClick(context)
                    onTrainWithCrystals(cat.id, 20)
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6E4327)),
                border = BorderStroke(1.dp, Color(0xFFFFD700))
            ) {
                Image(
                    painter = painterResource(R.drawable.crystals_128),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Train XP (+100 XP / 20 Crystals)",
                    color = Color.White,
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold
                )
            }
            Text(
                text = stringResource(cat.description),
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
                            text = "${cat.health.toInt()}",
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
                            text = "${cat.defense.toInt()}",
                            style = textSize,
                            modifier = Modifier.padding(4.dp),
                        )
                        Text(
                            text = stringResource(R.string.cat_desc_role),
                            style = textSize,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(4.dp)
                        )
                        Text(
                            text = stringResource(cat.role.res),
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
                            text = stringResource(cat.armorType.res),
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
                            text = "${cat.attack.toInt()}",
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
                            text = "${cat.speed}",
                            style = textSize,
                            modifier = Modifier.padding(4.dp),
                        )
                        Text(
                            text = stringResource(R.string.cat_desc_rarity),
                            style = textSize,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(4.dp)
                        )
                        Text(
                            text = stringResource(cat.rarity.res),
                            style = textSize,
                            modifier = Modifier.padding(4.dp),
                        )
                    }
                }
            }

            if (cat.abilities.isNotEmpty()) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth()
                ) {
                    for (ability in cat.abilities) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(8.dp)
                        ) {
                            Image(
                                painter = painterResource(ability.icon),
                                contentDescription = stringResource(R.string.ability_icon_desc,ability.name),
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
fun ArmoryCatDetailsCardPreview(){
    CatBattleGameTheme {
        ArmoryCatDetailsCard(
            cat = DetailedArmoryCatData(
                id = 1,
                name = R.string.the_swordsman_name,
                title = R.string.the_kitten_swordman,
                description = R.string.the_kitten_swordsman_desc,
                image = R.drawable.kitten_swordman_300x300,
                level = 1,
                experience = 0,
                health = 50f,
                attack = 15f,
                defense = 10f,
                speed = 1.2f,
                role = CatRole.WARRIOR,
                armorType = ArmorType.LIGHT,
                rarity = CatRarity.KITTEN,
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
                )
            )
        )
    }
}