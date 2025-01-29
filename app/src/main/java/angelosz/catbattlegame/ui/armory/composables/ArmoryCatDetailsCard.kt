package angelosz.catbattlegame.ui.armory.composables

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
import angelosz.catbattlegame.domain.enums.CatRarity
import angelosz.catbattlegame.domain.enums.CatRole
import angelosz.catbattlegame.ui.armory.data.DetailedArmoryCatData
import angelosz.catbattlegame.ui.components.ExperienceBar
import angelosz.catbattlegame.ui.theme.CatBattleGameTheme


@Composable
fun ArmoryCatDetailsCard(
    modifier: Modifier = Modifier,
    cat: DetailedArmoryCatData,
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
            ArmoryCatDetailsCatContent(
                cat = cat,
                imageSize = imageSize,
                textSize = textSize
            )
        }
    }
}

@Composable
private fun ArmoryCatDetailsCatContent(
    cat: DetailedArmoryCatData,
    imageSize: Int = 300,
    textSize: TextStyle = MaterialTheme.typography.bodyLarge
) {
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
                            text = stringResource(R.string.cat_desc_health, cat.health),
                            style = textSize,
                            modifier = Modifier.padding(4.dp),
                        )
                        Text(
                            text = stringResource(R.string.cat_desc_defense, cat.defense),
                            style = textSize,
                            modifier = Modifier.padding(4.dp)
                        )
                        Text(
                            text = stringResource(
                                R.string.cat_desc_role,
                                stringResource(cat.role.res)
                            ),
                            style = textSize,
                            modifier = Modifier.padding(4.dp)
                        )

                        Text(
                            text = stringResource(
                                R.string.cat_desc_armor,
                                stringResource(cat.armorType.res)
                            ),
                            style = textSize,
                            modifier = Modifier.padding(4.dp)
                        )
                    }
                    Column {
                        Text(
                            text = stringResource(R.string.cat_desc_attack, cat.attack),
                            style = textSize,
                            modifier = Modifier.padding(4.dp)
                        )
                        Text(
                            text = stringResource(R.string.cat_desc_attack_speed, cat.speed),
                            style = textSize,
                            modifier = Modifier.padding(4.dp)
                        )
                        Text(
                            text = stringResource(
                                R.string.cat_desc_rarity,
                                stringResource(cat.rarity.res)
                            ),
                            style = textSize,
                            modifier = Modifier.padding(4.dp)
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