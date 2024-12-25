package angelosz.catbattlegame.ui.combat

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import angelosz.catbattlegame.domain.enums.ArmorType
import angelosz.catbattlegame.domain.enums.CatRole
import angelosz.catbattlegame.domain.enums.CombatModifiers
import angelosz.catbattlegame.domain.enums.DamageType
import angelosz.catbattlegame.data.entities.Cat
import angelosz.catbattlegame.data.entities.EnemyCat
import angelosz.catbattlegame.data.entities.OwnedCat
import angelosz.catbattlegame.ui.components.SmallImageCard

class CombatCatData(
    val combatId: Int,
    @DrawableRes val image: Int,
    var currentHealth: Int,
    val maxHealth: Int,
    val healthModifier: Int,
    val attack: Int,
    val attackModifier: Int,
    val defense: Int,
    val defenseModifier: Int,
    val attackSpeed: Float,
    val attackSpeedModifier: Float,
    var initiative: Float,
    val abilities: List<CombatAbility>,
    val role: CatRole,
    val armorType: ArmorType,
    val combatModifiers: MutableMap<CombatModifiers, Int> = emptyMap<CombatModifiers, Int>().toMutableMap(),
    var lastEffect: Pair<CombatEffect, Int> = Pair(CombatEffect.NO_EFFECT, 0)
){
    companion object Builder{
        fun build(
            combatId: Int,
            cat: Cat,
            ownedCat: OwnedCat,
            combatAbilities: List<CombatAbility>,
        ): CombatCatData {
            return CombatCatData(
                combatId = combatId,
                image = cat.image,
                currentHealth = cat.baseHealth + ownedCat.healthModifier,
                maxHealth = cat.baseHealth + ownedCat.healthModifier,
                healthModifier = 0,
                attack = cat.baseAttack + ownedCat.attackModifier,
                attackModifier = 0,
                defense = cat.baseDefense + ownedCat.defenseModifier,
                defenseModifier = 0,
                attackSpeed = cat.attackSpeed + ownedCat.attackSpeedModifier,
                attackSpeedModifier = 0.0f,
                initiative = cat.attackSpeed + ownedCat.attackSpeedModifier,
                abilities = combatAbilities,
                role = cat.role,
                armorType = cat.armorType,
            )
        }

        fun build(
            combatId: Int,
            enemyCat: EnemyCat,
            combatAbilities: List<CombatAbility>
        ): CombatCatData {
            return CombatCatData(
                combatId = combatId,
                image = enemyCat.image,
                currentHealth = enemyCat.baseHealth,
                maxHealth = enemyCat.baseHealth,
                healthModifier = 0,
                attack = enemyCat.baseAttack,
                attackModifier = 0,
                defense = enemyCat.baseDefense,
                defenseModifier = 0,
                attackSpeed = enemyCat.attackSpeed,
                attackSpeedModifier = 0.0f,
                initiative = enemyCat.attackSpeed,
                abilities = combatAbilities,
                role = enemyCat.role,
                armorType = enemyCat.armorType
            )
        }
    }

    fun calculateHealthPercentage(): Float {
       return currentHealth.toFloat() / (maxHealth + healthModifier).toFloat()
    }
}

interface CombatCat{
    val cat: CombatCatData
    val onDeath: (Int) -> Unit

    fun tick(deltaTime: Float) {} // ???

    @Composable
    fun Display(
        imageSize: Int,
        barHeight: Int,
        onCardClicked: (Int) -> Unit,
        @DrawableRes border: Int
    ){
        Box(
            modifier = Modifier
                .width(imageSize.dp)
                .height((imageSize + 16).dp),
            contentAlignment = Alignment.Center,
        ){
            SmallImageCard(
                id = cat.combatId,
                onCardClicked = onCardClicked,
                imageSize = imageSize,
                image = cat.image,
                borderImage = border,
            )
            LinearProgressIndicator(
                progress = { cat.calculateHealthPercentage() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(barHeight.dp)
                    .align(Alignment.BottomEnd),
                color = Color.Green,
                gapSize = 0.dp,
                strokeCap = StrokeCap.Square,
                trackColor = Color.LightGray,
            )
            when(cat.lastEffect.first){
                CombatEffect.DAMAGED -> {
                    Text(
                        text = "- ${cat.lastEffect.second}",
                        color = Color.Red,
                        style = MaterialTheme.typography.displaySmall.copy(
                            shadow = Shadow(
                                color = Color.Black,
                                offset = Offset(3f, 3f),
                                blurRadius = 1f
                            )
                        ),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                    )
                }
                CombatEffect.HEALED -> {
                    Text(
                        text = "+ ${cat.lastEffect.second}",
                        color = Color.Green,
                        style = MaterialTheme.typography.displaySmall.copy(
                            shadow = Shadow(
                                color = Color.Black,
                                offset = Offset(3f, 3f),
                                blurRadius = 1f
                            )
                        ),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                    )
                }
                CombatEffect.POISONED -> {}
                CombatEffect.ATTACKED -> {}
                CombatEffect.NO_EFFECT -> {}
            }
        }
    }

    fun takeDamage(damage: Int, damageType: DamageType){
        cat.currentHealth -= damage
        if(cat.currentHealth <= 0){
            cat.currentHealth = 0
            onDeath(cat.combatId)
        }
    }

    fun heal(damage: Int, damageType: DamageType){
        cat.currentHealth += damage
        if(cat.currentHealth > cat.maxHealth) cat.currentHealth = cat.maxHealth
    }

    fun reduceAbilitiesCooldown(by: Int = 1){
        cat.abilities.forEach { ability ->
            ability.reduceCooldown(by)
        }
    }

    fun reduceCombatModifierTurn(combatModifier: CombatModifiers) {
        val currentValue = cat.combatModifiers.getOrDefault(combatModifier, 0)

        if(currentValue == 0){
            cat.combatModifiers.remove(combatModifier)
        } else {
            cat.combatModifiers[combatModifier] = currentValue - 1
        }
    }

    fun clear() {
        cat.lastEffect = Pair(CombatEffect.NO_EFFECT, 0)
    }

    fun addCombatModifier(combatModifier: CombatModifiers) {
        val currentValue = cat.combatModifiers.getOrDefault(combatModifier, 0)
        cat.combatModifiers[combatModifier] = currentValue + 1
    }
}

class PlayerCombatCat(override val cat: CombatCatData, override val onDeath: (Int) -> Unit) : CombatCat {}

sealed class EnemyCombatCat(override val cat: CombatCatData) : CombatCat {
    abstract fun selectAbility(): CombatAbility
    abstract fun selectTargets(playerCats: List<Int>, enemyCats: List<Int>, ability: CombatAbility)
}

class SimpleEnemy(override val cat: CombatCatData, override val onDeath: (Int) -> Unit) : EnemyCombatCat(cat) {
    override fun selectAbility(): CombatAbility {
        val notOnCooldownAbilities = cat.abilities.filter { ability -> !ability.onCooldown()}
        return notOnCooldownAbilities.random()
    }

    override fun selectTargets(playerCats: List<Int>, enemyCats: List<Int>, ability: CombatAbility) {
        ability.selectAllyTeam(enemyCats)
        ability.setSelectedAllyCat(enemyCats.random())
        ability.selectEnemyTeam(playerCats)
        ability.setSelectedEnemyCat(playerCats.random())
    }
}