package angelosz.catbattlegame.domain.models.entities

import androidx.annotation.DrawableRes
import androidx.room.Entity
import androidx.room.PrimaryKey
import angelosz.catbattlegame.domain.enums.ArmorType
import angelosz.catbattlegame.domain.enums.CatRarity
import angelosz.catbattlegame.domain.enums.CatRole

@Entity(tableName = "cats")
data class Cat(
    @PrimaryKey
    val id: Int,
    val name: String,
    val description: String,
    val armorType: ArmorType,
    val role: CatRole,
    val baseHealth: Int,
    val baseAttack: Int,
    val baseDefense: Int,
    val attackSpeed: Float,
    @DrawableRes val image: Int,
    val rarity: CatRarity,
    val evolutionLevel: Int = 50,
    val nextEvolutionId: Int? = null,
    val evolutionItemId: Int? = null,
    val isElderOf: Int? = null
)
