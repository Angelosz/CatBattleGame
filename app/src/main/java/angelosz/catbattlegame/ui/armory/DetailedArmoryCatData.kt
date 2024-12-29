package angelosz.catbattlegame.ui.armory

import androidx.annotation.DrawableRes
import angelosz.catbattlegame.R
import angelosz.catbattlegame.data.entities.Ability
import angelosz.catbattlegame.domain.enums.ArmorType
import angelosz.catbattlegame.domain.enums.CatRarity
import angelosz.catbattlegame.domain.enums.CatRole

data class DetailedArmoryCatData (
    val id: Int = 0,
    val name: String = "Kitten Swordman",
    val description: String = "",
    val armorType: ArmorType = ArmorType.LIGHT,
    val role: CatRole = CatRole.WARRIOR,
    val health: Int = 10,
    val attack: Int = 1,
    val defense: Int = 1,
    val speed: Float = 1f,
    @DrawableRes val image: Int = R.drawable.kitten_swordman_300x300,
    val rarity: CatRarity = CatRarity.KITTEN,
    val abilities: List<Ability> = emptyList(),
    val level: Int = 1,
    val experience: Int = 0
)