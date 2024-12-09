package angelosz.catbattlegame.domain.models.entities

import androidx.annotation.DrawableRes
import androidx.room.Entity
import androidx.room.PrimaryKey
import angelosz.catbattlegame.R
import angelosz.catbattlegame.domain.enums.ArmorType
import angelosz.catbattlegame.domain.enums.CatRarity
import angelosz.catbattlegame.domain.enums.CatRole

@Entity(tableName = "cats")
data class Cat(
    @PrimaryKey
    val id: Int = 1,
    val name: String = "Kitten Swordman",
    val description: String = "\"A brave kitten wielding a wooden sword, eager to protect.\"",
    val armorType: ArmorType = ArmorType.MEDIUM,
    val role: CatRole = CatRole.WARRIOR,
    val baseHealth: Int = 50,
    val baseAttack: Int = 10,
    val baseDefense: Int = 10,
    val attackSpeed: Float = 1f,
    @DrawableRes val image: Int = R.drawable.kitten_swordman_300x300,
    val rarity: CatRarity = CatRarity.KITTEN,
    val evolutionLevel: Int = 50,
    val nextEvolutionId: Int? = null,
    val evolutionItemId: Int? = null,
    val isElderOf: Int? = null
)
