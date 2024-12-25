package angelosz.catbattlegame.domain.models

import angelosz.catbattlegame.data.entities.Ability
import angelosz.catbattlegame.data.entities.Cat

data class CatDetailsData(
    val cat: Cat,
    val abilities: List<Ability>,
    val nextEvolutionName: String,
    val isElderOf: String
)
