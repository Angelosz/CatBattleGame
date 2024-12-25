package angelosz.catbattlegame.data.entities

import androidx.room.Entity
import angelosz.catbattlegame.domain.enums.AbilityType
import angelosz.catbattlegame.domain.enums.ArmorType

@Entity(primaryKeys = ["abilityType", "armorType"], tableName = "ability_armor_damage_multiplier")
data class AbilityArmorDamageMultiplier(
    val abilityType: AbilityType,
    val armorType: ArmorType,
    val multiplier: Float
)
