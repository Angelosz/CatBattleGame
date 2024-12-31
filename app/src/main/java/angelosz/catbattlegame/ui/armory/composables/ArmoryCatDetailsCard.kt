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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
import angelosz.catbattlegame.ui.components.RoundedTextButton
import angelosz.catbattlegame.ui.theme.CatBattleGameTheme


@Composable
fun ArmoryCatDetailsCard(
    modifier: Modifier = Modifier,
    cat: DetailedArmoryCatData,
    showCloseButton: Boolean = false,
    onCloseClicked: () -> Unit,
) {
    Card(
        modifier = modifier,
        shape = RectangleShape
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            ArmoryCatDetailsCatContent(cat)

            if(showCloseButton){
                RoundedTextButton(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp),
                    text = "Hide",
                    size = 64,
                    onClick = onCloseClicked,
                    textStyle = MaterialTheme.typography.headlineSmall
                )
            }

        }
    }
}

@Composable
private fun ArmoryCatDetailsCatContent(
    cat: DetailedArmoryCatData,
    imageSize: Int = 300
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = cat.name,
            modifier = Modifier.padding(16.dp),
            color = Color.White,
            style = MaterialTheme.typography.headlineSmall.copy(
                shadow = Shadow(
                    color = Color.Black,
                    offset = Offset(3f, 3f),
                    blurRadius = 1f
                )
            ),
            fontWeight = FontWeight.Bold,
        )
        Image(
            painter = painterResource(cat.image),
            contentDescription = cat.name,
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
                text = "\"${cat.description}\"",
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
                            text = "Base Health: ${cat.health}",
                            modifier = Modifier.padding(4.dp),
                        )
                        Text(
                            text = "Base Defense: ${cat.defense}",
                            modifier = Modifier.padding(4.dp)
                        )
                        Text(
                            text = "Role: ${stringResource(cat.role.res)}",
                            modifier = Modifier.padding(4.dp)
                        )

                        Text(
                            text = "Armor type: ${stringResource(cat.armorType.res)}",
                            modifier = Modifier.padding(4.dp)
                        )

                    }
                    Column {
                        Text(
                            text = "Base Attack: ${cat.attack}",
                            modifier = Modifier.padding(4.dp)
                        )
                        Text(
                            text = "Attack Speed: ${cat.speed}s",
                            modifier = Modifier.padding(4.dp)
                        )
                        Text(
                            text = "Rarity: ${stringResource(cat.rarity.res)}",
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
                                contentDescription = "${ability.name} ability icon",
                                modifier = Modifier.size(48.dp)
                            )
                            Text(
                                text = ability.name,
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
                name = "Kitten Swordsman",
                description = "A brave kitten wielding a wooden sword, eager to protect.",
                image = R.drawable.kitten_swordman_300x300,
                level = 1,
                experience = 0,
                health = 50,
                attack = 15,
                defense = 10,
                speed = 1.2f,
                role = CatRole.WARRIOR,
                armorType = ArmorType.LIGHT,
                rarity = CatRarity.KITTEN,
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
                )
            ),
            onCloseClicked = { }
        )
    }
}