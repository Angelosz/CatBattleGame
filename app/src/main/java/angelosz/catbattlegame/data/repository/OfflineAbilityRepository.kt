package angelosz.catbattlegame.data.repository

import angelosz.catbattlegame.data.dao.AbilityDao
import angelosz.catbattlegame.data.entities.Ability
import angelosz.catbattlegame.data.entities.CatAbilityCrossRef

class OfflineAbilityRepository(private val abilityDao: AbilityDao): AbilityRepository {
    override suspend fun getAllPlayerAbilities() = abilityDao.getAllPlayerAbilities()
    override suspend fun getAbilityById(id: Int) = abilityDao.getAbilityById(id)
    override suspend fun getCatAbilitiesPage(limit: Int, offset: Int) = abilityDao.getCatAbilitiesPage(limit, offset)
    override suspend fun getCount() = abilityDao.getCount()
    override suspend fun insertAbilities(abilities: List<Ability>) = abilityDao.insertAbilities(abilities)
    override suspend fun insertCatAbilityCrossRefs(catAbilityCrossRefs: List<CatAbilityCrossRef>) = abilityDao.insertCatAbilityCrossRefs(catAbilityCrossRefs)
    override suspend fun clearAbilitiesTable() = abilityDao.clearAbilitiesTable()
    override suspend fun clearAbilityCrossRefsTable() = abilityDao.clearAbilityCrossRefsTable()
    override suspend fun getCatAbilities(catId: Int) = abilityDao.getCatAbilities(catId)
    override suspend fun getEnemyCatAbilities(catId: Int): List<Ability> = abilityDao.getEnemyCatAbilities(catId)
}