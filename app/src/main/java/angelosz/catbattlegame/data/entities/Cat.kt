package angelosz.catbattlegame.data.entities

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
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
    @StringRes val name: Int = R.string.the_swordsman_name,
    @StringRes val title: Int = R.string.empty_title,
    @StringRes val description: Int = R.string.empty_title,
    val armorType: ArmorType = ArmorType.MEDIUM,
    val role: CatRole = CatRole.WARRIOR,
    val baseHealth: Float = 50f,
    val baseAttack: Float = 10f,
    val baseDefense: Float = 2f,
    val attackSpeed: Float = 1f,
    @DrawableRes val image: Int = R.drawable.kitten_swordman_300x300,
    val rarity: CatRarity = CatRarity.KITTEN,
    val evolutionLevel: Int = 50,
    val nextEvolutionId: Int? = null,
    val evolutionItemId: Int? = null,
    val isElderOf: Int? = null
)
