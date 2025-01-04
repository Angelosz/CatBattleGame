package angelosz.catbattlegame.data.repository

import angelosz.catbattlegame.data.entities.Ability

interface AbilityRepository {
    suspend fun getAllPlayerAbilities(): List<Ability>
    suspend fun getAbilityById(id: Int): Ability
    suspend fun getCatAbilitiesPage(limit: Int, offset: Int): List<Ability>
    suspend fun getCount(): Int
    suspend fun insertAbilities(abilities: List<Ability>)
    suspend fun getCatAbilities(catId: Int): List<Ability>
    suspend fun getEnemyCatAbilities(catId: Int): List<Ability>
}