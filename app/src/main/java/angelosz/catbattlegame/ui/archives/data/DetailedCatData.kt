package angelosz.catbattlegame.ui.archives.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import angelosz.catbattlegame.R
import angelosz.catbattlegame.data.entities.Ability
import angelosz.catbattlegame.data.entities.Cat
import angelosz.catbattlegame.domain.enums.ArmorType
import angelosz.catbattlegame.domain.enums.CatRarity
import angelosz.catbattlegame.domain.enums.CatRole
import angelosz.catbattlegame.utils.GameConstants.MAX_CAT_LEVEL

data class DetailedCatData(
    val id: Int = 0,
    val name: String = "Bob",
    @StringRes val title: Int = R.string.empty_title,
    val description: String = "",
    val armorType: ArmorType = ArmorType.MEDIUM,
    val role: CatRole = CatRole.WARRIOR,
    val baseHealth: Float = 50f,
    val baseAttack: Float = 10f,
    val baseDefense: Float = 10f,
    val attackSpeed: Float = 1f,
    @DrawableRes val image: Int = R.drawable.kitten_swordman_300x300,
    val rarity: CatRarity = CatRarity.KITTEN,
    val abilities: List<Ability> = emptyList(),
    val evolutionLevel: Int = MAX_CAT_LEVEL + 1,
    val nextEvolutionCat: Cat? = Cat(),
    val playerOwnsIt: Boolean = false
)