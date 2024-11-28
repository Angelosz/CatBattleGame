package angelosz.catbattlegame.domain.models.entities

import androidx.annotation.DrawableRes
import androidx.room.Entity
import androidx.room.PrimaryKey
import angelosz.catbattlegame.domain.enums.AbilityTarget
import angelosz.catbattlegame.domain.enums.AbilityType
import angelosz.catbattlegame.domain.enums.CombatModifiers

@Entity(tableName = "abilities")
data class Ability(
    @PrimaryKey val id: Int,
    val name: String,
    val description: String,
    val damageMultiplier: Float,
    val attackSpeedMultiplier: Float, //1 = 100% base attack speed.
    val combatModifier: CombatModifiers?, //CombatModifier, Turn duration
    val abilityType: AbilityType,
    val targets: AbilityTarget,
    val cooldown: Int,
    @DrawableRes val image: Int,
    @DrawableRes val icon: Int
)

