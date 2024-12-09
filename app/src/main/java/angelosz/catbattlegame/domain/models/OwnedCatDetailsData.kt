package angelosz.catbattlegame.domain.models

import angelosz.catbattlegame.domain.models.entities.Ability
import angelosz.catbattlegame.domain.models.entities.Cat
import angelosz.catbattlegame.domain.models.entities.OwnedCat

data class OwnedCatDetailsData(
    val ownedCatData: OwnedCat = OwnedCat(),
    val cat: Cat = Cat(),
    val abilities: List<Ability> = listOf(),
    val level: Int = 1,
    val experience: Int = 0,
    val evolutionCat: Pair<Int, String>? = null,
    val isElderOf: Pair<Int, String>? = null
)
