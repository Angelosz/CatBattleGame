package angelosz.catbattlegame.data.repository

import angelosz.catbattlegame.data.entities.Ability
import angelosz.catbattlegame.data.entities.CatAbilityCrossRef

interface AbilityRepository {
    suspend fun getAllPlayerAbilities(): List<Ability>
    suspend fun getAbilityById(id: Int): Ability
    suspend fun getCatAbilitiesPage(limit: Int, offset: Int): List<Ability>
    suspend fun getCount(): Int
    suspend fun insertAbilities(abilities: List<Ability>)
    suspend fun insertCatAbilityCrossRefs(catAbilityCrossRefs: List<CatAbilityCrossRef>)
    suspend fun clearAbilitiesTable()
    suspend fun clearAbilityCrossRefsTable()
    suspend fun getCatAbilities(catId: Int): List<Ability>
    suspend fun getEnemyCatAbilities(catId: Int): List<Ability>
}