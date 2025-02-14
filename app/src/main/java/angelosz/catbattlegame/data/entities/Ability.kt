package angelosz.catbattlegame.data.entities

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.room.Entity
import androidx.room.PrimaryKey
import angelosz.catbattlegame.R
import angelosz.catbattlegame.domain.enums.AbilitySource
import angelosz.catbattlegame.domain.enums.AbilityTarget
import angelosz.catbattlegame.domain.enums.AbilityType
import angelosz.catbattlegame.domain.enums.CombatModifier
import angelosz.catbattlegame.domain.enums.DamageType

@Entity(tableName = "abilities")
data class Ability(
    @PrimaryKey val id: Int,
    @StringRes val name: Int = R.string.ability_quick_attack,
    @StringRes val description: Int = R.string.ability_quick_attack_desc,
    val damageMultiplier: Float = 1f,
    val attackSpeedMultiplier: Float = 1f, //1 = 100% base attack speed.
    val combatModifier: CombatModifier? = null, //CombatModifier, Turn duration
    val combatModifierDuration: Int = 1,
    val combatModifierValue: Float = 1f,
    val abilityType: AbilityType = AbilityType.DAMAGE,
    val damageType: DamageType = DamageType.PIERCING,
    val targets: AbilityTarget = AbilityTarget.SINGLE_ENEMY,
    val cooldown: Int = 0,
    @DrawableRes val image: Int = R.drawable.ability_quick_attack_256,
    @DrawableRes val icon: Int = R.drawable.ability_quick_attack_48,
    val abilitySource: AbilitySource = AbilitySource.PLAYER,
)

