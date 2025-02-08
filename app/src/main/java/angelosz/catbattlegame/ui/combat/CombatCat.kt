package angelosz.catbattlegame.ui.combat

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import angelosz.catbattlegame.R
import angelosz.catbattlegame.data.entities.Ability
import angelosz.catbattlegame.data.entities.Cat
import angelosz.catbattlegame.data.entities.EnemyCat
import angelosz.catbattlegame.data.entities.OwnedCat
import angelosz.catbattlegame.domain.enums.AbilityType
import angelosz.catbattlegame.domain.enums.ArmorType
import angelosz.catbattlegame.domain.enums.CatRole
import angelosz.catbattlegame.domain.enums.CombatModifier
import angelosz.catbattlegame.domain.enums.DamageType
import angelosz.catbattlegame.ui.components.CatCard

class CombatCatData(
    val combatId: Int,
    val id: Int,
    @StringRes val name: Int,
    @DrawableRes val image: Int,
    var currentHealth: Float,
    val maxHealth: Float,
    var healthModifier: Float,
    val attack: Float,
    var attackModifier: Float,
    val defense: Float,
    var defenseModifier: Float,
    val attackSpeed: Float,
    var attackSpeedModifier: Float,
    var initiative: Float,
    val abilities: List<CombatAbility>,
    val role: CatRole,
    val armorType: ArmorType,
    val appliedCombatModifiers: MutableList<appliedCombatModifier> = mutableListOf(),
    var lastCombatActionTaken: CombatAction = CombatAction(CombatActionTaken.NO_EFFECT, 0, DamageType.BLUNT)
){
    val getMaxHealth: Float
        get() = maxHealth + healthModifier
    val getAttack: Float
        get() = attack + attackModifier
    val getDefense: Float
        get() = defense + defenseModifier
    val getAttackSpeed: Float
        get() = attackSpeed + attackSpeedModifier

    companion object Builder{
        fun build(
            combatId: Int,
            cat: Cat,
            ownedCat: OwnedCat,
            combatAbilities: List<CombatAbility>,
        ): CombatCatData {
            return CombatCatData(
                combatId = combatId,
                id = cat.id,
                name = cat.name,
                image = cat.image,
                currentHealth = cat.baseHealth + ownedCat.healthModifier,
                maxHealth = cat.baseHealth + ownedCat.healthModifier,
                healthModifier = 0f,
                attack = cat.baseAttack + ownedCat.attackModifier,
                attackModifier = 0f,
                defense = cat.baseDefense + ownedCat.defenseModifier,
                defenseModifier = 0f,
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
                id = enemyCat.id.toInt(),
                name = enemyCat.name,
                image = enemyCat.image,
                currentHealth = enemyCat.baseHealth,
                maxHealth = enemyCat.baseHealth,
                healthModifier = 0f,
                attack = enemyCat.baseAttack,
                attackModifier = 0f,
                defense = enemyCat.baseDefense,
                defenseModifier = 0f,
                attackSpeed = enemyCat.attackSpeed,
                attackSpeedModifier = 0.0f,
                initiative = enemyCat.attackSpeed,
                abilities = combatAbilities,
                role = enemyCat.role,
                armorType = enemyCat.armorType
            )
        }

        fun copy(
            newCombatId: Int,
            combatCatData: CombatCatData
        ): CombatCatData = CombatCatData(
            combatId = newCombatId,
            id = combatCatData.id,
            name = combatCatData.name,
            image = combatCatData.image,
            currentHealth = combatCatData.currentHealth,
            maxHealth = combatCatData.maxHealth,
            healthModifier = combatCatData.healthModifier,
            attack = combatCatData.attack,
            attackModifier = combatCatData.attackModifier,
            defense = combatCatData.defense,
            defenseModifier = combatCatData.defenseModifier,
            attackSpeed = combatCatData.attackSpeed,
            attackSpeedModifier = combatCatData.attackSpeedModifier,
            initiative = combatCatData.initiative,
            abilities = combatCatData.abilities,
            role = combatCatData.role,
            armorType = combatCatData.armorType,
        )
    }

    fun calculateHealthPercentage(): Float {
       return currentHealth / (maxHealth + healthModifier)
    }

    fun resetStatModifiers() {
        resetHealth()
        attackModifier = 0f
        defenseModifier = 0f
        attackSpeedModifier = 0.0f
    }

    fun modifyHealth(amount: Int) {
        val healthPercentage = calculateHealthPercentage()
        healthModifier += healthModifier + amount
        val newHealth = getMaxHealth * healthPercentage
        currentHealth = newHealth
    }

    fun resetHealth(){
        val healthPercentage = calculateHealthPercentage()
        healthModifier = 0f
        val newHealth = getMaxHealth * healthPercentage
        currentHealth = newHealth
    }
}

data class CombatAction(
    val combatAction: CombatActionTaken,
    val amount: Int,
    val damageType: DamageType
)
class appliedCombatModifier(
    val combatModifier: CombatModifier,
    val amount: Float = 0.0f,
    var duration: Int = 1
){
    fun reduceDuration() {
        duration -= 1
    }

    fun hasEnded() = duration <= 0
}

interface CombatCat{
    val cat: CombatCatData
    val onDeath: (Int) -> Unit

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
            CatCard(
                id = cat.combatId,
                onCardClicked = onCardClicked,
                imageSize = imageSize,
                image = cat.image,
                borderImage = border,
            )
            Row(
                modifier = Modifier.align(Alignment.TopStart)
            ){
                cat.appliedCombatModifiers.forEach{ appliedCombatModifier ->
                    Image(
                        painter = painterResource(
                            when(appliedCombatModifier.combatModifier){
                                CombatModifier.SLOWED -> R.drawable.icon_slow
                                CombatModifier.STUNNED -> R.drawable.stun_icon
                                CombatModifier.POISONED -> R.drawable.poison_icon
                                CombatModifier.CLEANSED -> R.drawable.goldcoins_128
                                CombatModifier.SHIELDED -> R.drawable.divine_shield_icon
                                else -> if(appliedCombatModifier.amount > 0) R.drawable.green_arrow_up else R.drawable.red_arrow_down
                            }),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                }

            }
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
            when(cat.lastCombatActionTaken.combatAction){
                CombatActionTaken.DAMAGED -> {
                    Row(
                        modifier = Modifier
                            .align(Alignment.BottomEnd),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "- ${cat.lastCombatActionTaken.amount}",
                            color = Color.Red,
                            style = MaterialTheme.typography.displaySmall.copy(
                                shadow = Shadow(
                                    color = Color.Black,
                                    offset = Offset(3f, 3f),
                                    blurRadius = 1f
                                )
                            ),
                            fontWeight = FontWeight.Bold
                        )
                        Image(
                            painter = painterResource(
                                when(cat.lastCombatActionTaken.damageType){
                                    DamageType.ELEMENTAL -> R.drawable.fire_icon
                                    DamageType.POISON -> R.drawable.poison_icon
                                    else -> R.drawable.piercing_damage_icon
                                }
                            ),
                            contentDescription = null,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
                CombatActionTaken.HEALED -> {
                    Text(
                        text = "+ ${cat.lastCombatActionTaken.amount}",
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
                CombatActionTaken.POISONED -> {
                    Row(
                        modifier = Modifier
                            .align(Alignment.BottomEnd),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "- ${cat.lastCombatActionTaken.amount}",
                            color = Color.Green,
                            style = MaterialTheme.typography.displaySmall.copy(
                                shadow = Shadow(
                                    color = Color.Black,
                                    offset = Offset(3f, 3f),
                                    blurRadius = 1f
                                )
                            ),
                            fontWeight = FontWeight.Bold,
                        )
                        Image(
                            painter = painterResource(R.drawable.poison_icon),
                            contentDescription = null,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
                CombatActionTaken.ATTACKED -> {}
                CombatActionTaken.NO_EFFECT -> {}
                CombatActionTaken.SHIELDED -> {
                    Row(
                        modifier = Modifier
                            .align(Alignment.BottomEnd),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${cat.lastCombatActionTaken.amount}",
                            color = Color.Yellow,
                            style = MaterialTheme.typography.displaySmall.copy(
                                shadow = Shadow(
                                    color = Color.Black,
                                    offset = Offset(3f, 3f),
                                    blurRadius = 1f
                                )
                            ),
                            fontWeight = FontWeight.Bold,
                        )
                        Image(
                            painter = painterResource(R.drawable.divine_shield_icon),
                            contentDescription = null,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
            }
        }
    }

    fun takeDamage(damage: Int, damageType: DamageType){
        cat.currentHealth -= damage
        if(cat.currentHealth <= 0){
            cat.currentHealth = 0f
            onDeath(cat.combatId)
        }
    }

    fun heal(damage: Int, damageType: DamageType){
        cat.currentHealth += damage
        if(cat.currentHealth > cat.getMaxHealth) cat.currentHealth = cat.maxHealth
    }

    fun reduceAbilitiesCooldown(by: Int = 1){
        cat.abilities.forEach { ability ->
            ability.reduceCooldown(by)
        }
    }

    fun clear() {
        cat.lastCombatActionTaken = CombatAction(CombatActionTaken.NO_EFFECT, 0, DamageType.PIERCING)
    }

    fun addCombatModifier(appliedCombatModifier: appliedCombatModifier) {
        cat.appliedCombatModifiers.add(appliedCombatModifier)
    }

    fun checkAppliedCombatModifiersDuration(){
        cat.appliedCombatModifiers.removeIf { it.hasEnded() }
    }

    fun isSlowed(): Boolean {
        cat.appliedCombatModifiers.forEach {
            if(it.combatModifier == CombatModifier.SLOWED) return true
        }
        return false
    }

    fun isShielded(): Boolean {
        cat.appliedCombatModifiers.forEach {
            if(it.combatModifier == CombatModifier.SHIELDED) return true
        }
        return false
    }

    fun isStunned(): Boolean {
        cat.appliedCombatModifiers.forEach {
            if(it.combatModifier == CombatModifier.STUNNED) return true
        }
        return false
    }

    fun reduceStun() {
        cat.appliedCombatModifiers.forEach {
            if(it.combatModifier == CombatModifier.STUNNED) it.reduceDuration()
        }
        checkAppliedCombatModifiersDuration()
    }


    fun shieldAbsorbedDamage() {
        cat.appliedCombatModifiers.forEach{
            if (it.combatModifier == CombatModifier.SHIELDED){
                cat.appliedCombatModifiers.remove(it)
                return
            }
        }
    }

    fun reduceSlowDuration() {
        cat.appliedCombatModifiers.forEach {
            if(it.combatModifier == CombatModifier.SLOWED) it.reduceDuration()
        }
        checkAppliedCombatModifiersDuration()
    }

    fun reduceBuffsDuration() {
        cat.appliedCombatModifiers.forEach {
            when(it.combatModifier){
                CombatModifier.HEALTH_MODIFIED,
                CombatModifier.ATTACK_MODIFIED,
                CombatModifier.DEFENSE_MODIFIED,
                CombatModifier.SPEED_MODIFIED -> it.reduceDuration()
                else -> {}
            }
        }
        checkAppliedCombatModifiersDuration()
    }

    fun refreshBuffModifiers() {
        cat.resetStatModifiers()
        cat.appliedCombatModifiers.forEach {
            when(it.combatModifier){
                CombatModifier.HEALTH_MODIFIED -> cat.modifyHealth(it.amount.toInt())
                CombatModifier.ATTACK_MODIFIED -> cat.attackModifier += it.amount.toInt()
                CombatModifier.DEFENSE_MODIFIED -> cat.defenseModifier += it.amount.toInt()
                CombatModifier.SPEED_MODIFIED -> cat.attackSpeedModifier += it.amount
                else -> {}
            }
        }
    }

    fun getSlowAmount(): Float {
        var slowAmount = 0.0f
        cat.appliedCombatModifiers.forEach {
            if(it.combatModifier == CombatModifier.SLOWED) slowAmount += it.amount
        }
        return slowAmount
    }
}

class PlayerCombatCat(override val cat: CombatCatData, override val onDeath: (Int) -> Unit) : CombatCat {}

sealed class EnemyCombatCat(override val cat: CombatCatData) : CombatCat {
    abstract fun selectAbility(playerCats: List<CombatCat>, enemyCats: List<CombatCat>): CombatAbility
    open fun selectTargets(playerCats: List<CombatCat>, enemyCats: List<CombatCat>, ability: CombatAbility){
        ability.selectAllyTeam(enemyCats.map { it.cat.combatId })
        ability.setSelectedAllyCat(enemyCats.random().cat.combatId)
        ability.selectEnemyTeam(playerCats.map { it.cat.combatId })
        ability.setSelectedEnemyCat(playerCats.random().cat.combatId)
    }
}

class SimpleEnemy(override val cat: CombatCatData, override val onDeath: (Int) -> Unit) : EnemyCombatCat(cat) {
    override fun selectAbility(
        playerCats: List<CombatCat>,
        enemyCats: List<CombatCat>
    ): CombatAbility {
        val notOnCooldownAbilities = cat.abilities.filter { ability -> !ability.onCooldown()}
        if(notOnCooldownAbilities.isEmpty()) return EmptyAbility(Ability(0))
        val ability = notOnCooldownAbilities.random()
        selectTargets(playerCats, enemyCats, ability)
        return ability
    }

}

class SummonerEnemy(override val cat: CombatCatData, override val onDeath: (Int) -> Unit) : EnemyCombatCat(cat) {
    override fun selectAbility(
        playerCats: List<CombatCat>,
        enemyCats: List<CombatCat>
    ): CombatAbility {
        var selectedAbility = cat.abilities.find { ability ->
            (ability.ability.abilityType == AbilityType.SUMMON
                    || ability.ability.abilityType == AbilityType.CLONE)
                    && !ability.onCooldown()
        }
        if(selectedAbility != null && enemyCats.size < 4){
            selectTargets(playerCats, enemyCats, selectedAbility)
            return selectedAbility
        }

        val notOnCooldownAbilities = cat.abilities.filter {
            ability -> !ability.onCooldown()
                && ability.ability.abilityType != AbilityType.SUMMON
                && ability.ability.abilityType != AbilityType.CLONE
        }
        if(notOnCooldownAbilities.isEmpty()) return EmptyAbility(Ability(0))
        selectedAbility = notOnCooldownAbilities.random()
        selectTargets(playerCats, enemyCats, selectedAbility)
        return selectedAbility
    }

}

class UniqueSummonEnemy(override val cat: CombatCatData, override val onDeath: (Int) -> Unit) : EnemyCombatCat(cat) {
    override fun selectAbility(
        playerCats: List<CombatCat>,
        enemyCats: List<CombatCat>
    ): CombatAbility {
        var selectedAbility = cat.abilities.find { ability -> ability.ability.abilityType == AbilityType.SUMMON }
        if(selectedAbility != null && !selectedAbility.onCooldown() && enemyCats.size < 4){
            val summonId = selectedAbility.ability.combatModifierValue
            if(enemyCats.find { cat -> cat.cat.id == summonId.toInt()} == null){
                selectTargets(playerCats, enemyCats, selectedAbility)
                return selectedAbility
            }
        }

        val notOnCooldownAbilities = cat.abilities.filter {
                ability -> !ability.onCooldown()
                && ability.ability.abilityType != AbilityType.SUMMON
        }
        if(notOnCooldownAbilities.isEmpty()) return EmptyAbility(Ability(0))
        selectedAbility = notOnCooldownAbilities.random()
        selectTargets(playerCats, enemyCats, selectedAbility)
        return selectedAbility
    }

}