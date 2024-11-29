package angelosz.catbattlegame.domain.models

import angelosz.catbattlegame.domain.models.entities.Ability
import angelosz.catbattlegame.domain.models.entities.Cat

data class CatDetailsData(
    val cat: Cat,
    val abilities: List<Ability>,
    val nextEvolutionName: String,
    val isElderOf: String
)
