package angelosz.catbattlegame.domain.models

import angelosz.catbattlegame.data.entities.Ability
import angelosz.catbattlegame.data.entities.Cat
import angelosz.catbattlegame.data.entities.OwnedCat

data class OwnedCatDetailsData(
    val ownedCatData: OwnedCat = OwnedCat(),
    val cat: Cat = Cat(),
    val abilities: List<Ability> = listOf(),
    val level: Int = 1,
    val experience: Int = 0,
    val evolutionCat: Pair<Int, String>? = null,
    val isElderOf: Pair<Int, String>? = null
)
