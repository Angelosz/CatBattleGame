package angelosz.catbattlegame.ui.archives.data

import androidx.annotation.DrawableRes
import angelosz.catbattlegame.R
import angelosz.catbattlegame.data.entities.Ability
import angelosz.catbattlegame.data.entities.Cat
import angelosz.catbattlegame.domain.enums.ArmorType
import angelosz.catbattlegame.domain.enums.CatRarity
import angelosz.catbattlegame.domain.enums.CatRole
import angelosz.catbattlegame.utils.GameConstants.MAX_CAT_LEVEL

data class DetailedCatData(
    val id: Int = 0,
    val name: String = "",
    val description: String = "",
    val armorType: ArmorType = ArmorType.MEDIUM,
    val role: CatRole = CatRole.WARRIOR,
    val baseHealth: Int = 50,
    val baseAttack: Int = 10,
    val baseDefense: Int = 10,
    val attackSpeed: Float = 1f,
    @DrawableRes val image: Int = R.drawable.kitten_swordman_300x300,
    val rarity: CatRarity = CatRarity.KITTEN,
    val abilities: List<Ability> = emptyList(),
    val evolutionLevel: Int = MAX_CAT_LEVEL + 1,
    val nextEvolutionCat: Cat? = Cat(),
)