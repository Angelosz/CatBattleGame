package angelosz.catbattlegame.data.repository

import angelosz.catbattlegame.domain.models.entities.Ability

interface AbilityRepository {
    suspend fun getAllAbilities(): List<Ability>

    suspend fun getAbilityById(id: Int): Ability

    suspend fun insertAbilities(abilities: List<Ability>)

    suspend fun getCatAbilities(catId: Int): List<Ability>
}