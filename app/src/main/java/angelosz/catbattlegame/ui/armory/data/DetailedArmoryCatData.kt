package angelosz.catbattlegame.ui.armory.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import angelosz.catbattlegame.R
import angelosz.catbattlegame.data.entities.Ability
import angelosz.catbattlegame.domain.enums.ArmorType
import angelosz.catbattlegame.domain.enums.CatRarity
import angelosz.catbattlegame.domain.enums.CatRole

data class DetailedArmoryCatData(
    val id: Int = 0,
    @StringRes val name: Int = R.string.the_swordsman_name,
    @StringRes val title: Int = R.string.empty_title,
    @StringRes val description: Int = R.string.the_swordsman_desc,
    val armorType: ArmorType = ArmorType.LIGHT,
    val role: CatRole = CatRole.WARRIOR,
    val health: Float = 10f,
    val attack: Float = 1f,
    val defense: Float = 1f,
    val speed: Float = 1f,
    @DrawableRes val image: Int = R.drawable.kitten_swordman_300x300,
    val rarity: CatRarity = CatRarity.KITTEN,
    val abilities: List<Ability> = emptyList(),
    val level: Int = 1,
    val experience: Int = 0,
)