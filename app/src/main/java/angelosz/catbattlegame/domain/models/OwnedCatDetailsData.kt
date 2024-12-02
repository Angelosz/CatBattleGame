package angelosz.catbattlegame.domain.models

import angelosz.catbattlegame.domain.models.entities.Ability
import angelosz.catbattlegame.domain.models.entities.Cat

data class OwnedCatDetailsData(
    val cat: Cat,
    val abilities: List<Ability>,
    val level: Int,
    val experience: Int,
    val evolutionCat: Pair<Int, String>?
)
