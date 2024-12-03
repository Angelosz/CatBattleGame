package angelosz.catbattlegame.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import angelosz.catbattlegame.R
import angelosz.catbattlegame.domain.enums.AbilityTarget
import angelosz.catbattlegame.domain.enums.AbilityType
import angelosz.catbattlegame.domain.enums.ArmorType
import angelosz.catbattlegame.domain.enums.CatRarity
import angelosz.catbattlegame.domain.enums.CatRole
import angelosz.catbattlegame.domain.models.CatDetailsData
import angelosz.catbattlegame.domain.models.entities.Ability
import angelosz.catbattlegame.domain.models.entities.Cat
import angelosz.catbattlegame.ui.theme.CatBattleGameTheme

@Composable
fun CatDataDetailsCard(
    modifier: Modifier = Modifier,
    catDetails: CatDetailsData,
    showExperienceBar: Boolean = false,
    level: Int = 1,
    experience: Int = 0
    ) {
    Card(
        modifier = modifier
            .fillMaxSize(),
        shape = RectangleShape
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = catDetails.cat.name,
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.headlineSmall
            )
            Image(
                painter = painterResource(catDetails.cat.image),
                contentDescription = catDetails.cat.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
            )
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                if(showExperienceBar){
                    ExperienceBar(
                        level = level,
                        experience = experience,
                        showText = true
                    )
                }
                Text(
                    text = catDetails.cat.description,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(8.dp)
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
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column() {
                            Text(
                                text = "Base Health: ${catDetails.cat.baseHealth}",
                                modifier = Modifier.padding(4.dp),
                            )
                            Text(
                                text = "Base Defense: ${catDetails.cat.baseDefense}",
                                modifier = Modifier.padding(4.dp)
                            )
                            Text(
                                text = "Role: ${catDetails.cat.role}",
                                modifier = Modifier.padding(4.dp)
                            )

                            Text(
                                text = "Armor type: ${catDetails.cat.armorType}",
                                modifier = Modifier.padding(4.dp)
                            )

                        }
                        Column {
                            Text(
                                text = "Base Attack: ${catDetails.cat.baseAttack}",
                                modifier = Modifier.padding(4.dp)
                            )
                            Text(
                                text = "Attack Speed: ${catDetails.cat.attackSpeed}s",
                                modifier = Modifier.padding(4.dp)
                            )
                            Text(
                                text = "Rarity: ${catDetails.cat.rarity}",
                                modifier = Modifier.padding(4.dp)
                            )
                            if (catDetails.isElderOf.isNotBlank()) {
                                Text(
                                    text = "Elder Of: ${catDetails.isElderOf}",
                                    modifier = Modifier.padding(4.dp)
                                )
                            }
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
                    if (catDetails.cat.evolutionLevel <= 30) {
                        Text(
                            text = "Evolves at level: ${catDetails.cat.evolutionLevel}",
                            modifier = Modifier.padding(top = 8.dp, start = 8.dp)
                        )
                        Text(
                            text = "Evolution: ${catDetails.nextEvolutionName}",
                            modifier = Modifier.padding(top = 4.dp, bottom = 8.dp, start = 8.dp)
                        )
                    }
                }

                if (catDetails.abilities.isNotEmpty()) {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        ),
                        modifier = Modifier
                            .padding(4.dp)
                            .fillMaxWidth()
                    ) {
                        for (ability in catDetails.abilities) {
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
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Preview_CatDataCard(){
    CatBattleGameTheme {
        CatDataDetailsCard(
            modifier = Modifier,
            CatDetailsData(
                cat = Cat(
                    id = 1,
                    name = "Kitten Swordsman",
                    description = "\"A brave kitten wielding a wooden sword, eager to protect.\"",
                    armorType = ArmorType.LIGHT,
                    role = CatRole.WARRIOR,
                    baseHealth = 50,
                    baseAttack = 15,
                    baseDefense = 10,
                    attackSpeed = 1.2f,
                    image = R.drawable.kitten_swordman_300x300,
                    rarity = CatRarity.KITTEN,
                    evolutionLevel = 5,
                    nextEvolutionId = 2
                ),
                abilities = listOf(
                    Ability(
                        id = 1,
                        name = "Quick Attack",
                        description = "Quick but lower damage attack.",
                        image = R.drawable.ability_quick_attack_256,
                        icon = R.drawable.ability_quick_attack_48,
                        abilityType = AbilityType.NORMAL,
                        attackSpeedMultiplier = 0.75f,
                        damageMultiplier = 0.75f,
                        combatModifier = null,
                        targets = AbilityTarget.SINGLE_ENEMY,
                        cooldown = 0
                    )
                ),
                isElderOf = "",
                nextEvolutionName = "Teen Swordsman"
            )
        )
    }
}