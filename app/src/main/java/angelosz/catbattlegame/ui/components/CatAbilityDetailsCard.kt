package angelosz.catbattlegame.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import angelosz.catbattlegame.R
import angelosz.catbattlegame.domain.enums.AbilityTarget
import angelosz.catbattlegame.domain.enums.AbilityType
import angelosz.catbattlegame.domain.enums.CombatModifiers
import angelosz.catbattlegame.domain.models.entities.Ability
import angelosz.catbattlegame.ui.theme.CatBattleGameTheme

@Composable
fun CatAbilityDetailsCard(modifier: Modifier = Modifier, ability: Ability){
    Card(
        modifier = modifier
        .shadow(2.dp, RoundedCornerShape(8.dp)),
        shape = RoundedCornerShape(8.dp)
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = ability.name,
                modifier = Modifier.padding(32.dp),
                style = MaterialTheme.typography.headlineSmall
            )
            Image (
                painter = painterResource(ability.image),
                contentDescription = ability.name,
                Modifier.height(256.dp).fillMaxWidth()
            )
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = ability.description,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(8.dp)
                )
                Text(
                    text = "Damage multiplier: ${ability.damageMultiplier}"
                )
                Text(
                    text = "Attack speed multiplier: ${ability.attackSpeedMultiplier}"
                )
                Text(
                    text = "Combat Modifier: ${ability.combatModifier?.res?.let { stringResource(it) } ?: "None"}"
                )
                Text(
                    text = "Ability type: ${stringResource(ability.abilityType.res)}"
                )
                Text(
                    text = "Ability target/s: ${stringResource(ability.targets.res)}"
                )
                Text(
                    text = "Cooldown: ${ability.cooldown}"
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        text = "Ability icon: "
                    )
                    Image(
                        painter = painterResource(ability.icon),
                        contentDescription = "${ability.name} ability icon.",
                        Modifier.size(48.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CatAbilityCardPreview(){
    CatBattleGameTheme {
        CatAbilityDetailsCard(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            ability = Ability(
                id = 3,
                name = "Defend Ally",
                description = "Ally becomes SHIELDED for the next damage they receive.",
                image = R.drawable.ability_defend_ally_256,
                icon = R.drawable.ability_defend_ally_48,
                abilityType = AbilityType.STATUS_CHANGING,
                attackSpeedMultiplier = 1f,
                damageMultiplier = 1f,
                combatModifier = CombatModifiers.SHIELDED,
                targets = AbilityTarget.ALLY,
                cooldown = 3
            )
        )
    }
}