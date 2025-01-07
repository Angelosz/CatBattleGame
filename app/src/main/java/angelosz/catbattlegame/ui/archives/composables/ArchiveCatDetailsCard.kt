package angelosz.catbattlegame.ui.archives.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import angelosz.catbattlegame.data.entities.Cat
import angelosz.catbattlegame.domain.enums.ArmorType
import angelosz.catbattlegame.domain.enums.CatRarity
import angelosz.catbattlegame.domain.enums.CatRole
import angelosz.catbattlegame.ui.archives.data.DetailedCatData
import angelosz.catbattlegame.ui.theme.CatBattleGameTheme
import angelosz.catbattlegame.utils.GameConstants.MAX_CAT_LEVEL

@Composable
fun ArchiveCatDetailsCard(
    modifier: Modifier = Modifier,
    cat: DetailedCatData,
    textSize: TextStyle = MaterialTheme.typography.bodyLarge,
    imageSize: Int = 300,
    onAbilityClicked: (Int) -> Unit,
) {
    Card(
        modifier = modifier,
        shape = RectangleShape
    ) {
        Box(
            modifier = Modifier,
            contentAlignment = Alignment.Center,
        ) {
            ArchiveCatDetailsCatContent(
                cat = cat,
                imageSize = imageSize,
                textSize = textSize,
                onAbilityClicked = onAbilityClicked
            )
        }
    }
}

@Composable
private fun ArchiveCatDetailsCatContent(
    cat: DetailedCatData,
    imageSize: Int = 300,
    textSize: TextStyle = MaterialTheme.typography.bodyLarge,
    onAbilityClicked: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = stringResource(cat.title, cat.name),
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.headlineSmall,
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
            Text(
                text = cat.description,
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
                            text = "Health: ${cat.baseHealth}",
                            style = textSize,
                            modifier = Modifier.padding(4.dp),
                        )
                        Text(
                            text = "Defense: ${cat.baseDefense}",
                            style = textSize,
                            modifier = Modifier.padding(4.dp)
                        )
                        Text(
                            text = "Role: ${stringResource(cat.role.res)}",
                            style = textSize,
                            modifier = Modifier.padding(4.dp)
                        )

                        Text(
                            text = "Armor: ${stringResource(cat.armorType.res)}",
                            style = textSize,
                            modifier = Modifier.padding(4.dp)
                        )

                    }
                    Column {
                        Text(
                            text = "Attack: ${cat.baseAttack}",
                            style = textSize,
                            modifier = Modifier.padding(4.dp)
                        )
                        Text(
                            text = "A. Speed: ${cat.attackSpeed}s",
                            style = textSize,
                            modifier = Modifier.padding(4.dp)
                        )
                        Text(
                            text = "Rarity: ${stringResource(cat.rarity.res)}",
                            style = textSize,
                            modifier = Modifier.padding(4.dp)
                        )
                    }
                }
            }
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth()
            ) {
                if (cat.evolutionLevel <= MAX_CAT_LEVEL) {
                    Text(
                        text = "Evolves at level: ${cat.evolutionLevel}",
                        modifier = Modifier.padding(top = 8.dp, start = 16.dp)
                    )
                    Text(
                        text = "Evolution: ${cat.nextEvolutionCat?.name}",
                        modifier = Modifier.padding(top = 4.dp, bottom = 8.dp, start = 16.dp)
                    )
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
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                                .clickable(onClick = { onAbilityClicked(ability.id) })
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
fun ArmoryCatDetailsCardPreview(){
    CatBattleGameTheme {
        ArchiveCatDetailsCard(
            cat = DetailedCatData(
                id = 1,
                name = "Bob",
                title = R.string.the_kitten_swordman,
                description = "A brave kitten wielding a wooden sword, eager to protect.",
                image = R.drawable.kitten_swordman_300x300,
                baseHealth = 50,
                baseAttack = 15,
                baseDefense = 10,
                attackSpeed = 1.2f,
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
                ),
                evolutionLevel = 5,
                nextEvolutionCat = Cat(name= "Teen Swordman"),
            ),
            onAbilityClicked = {  }
        )
    }
}