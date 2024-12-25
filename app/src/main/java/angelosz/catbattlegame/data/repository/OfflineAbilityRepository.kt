package angelosz.catbattlegame.data.repository

import angelosz.catbattlegame.data.dao.AbilityDao
import angelosz.catbattlegame.data.entities.Ability

class OfflineAbilityRepository(private val abilityDao: AbilityDao): AbilityRepository {
    override suspend fun getAllPlayerAbilities() = abilityDao.getAllPlayerAbilities()
    override suspend fun getAbilityById(id: Int) = abilityDao.getAbilityById(id)
    override suspend fun insertAbilities(abilities: List<Ability>) = abilityDao.insertAbilities(abilities)
    override suspend fun getCatAbilities(catId: Int) = abilityDao.getCatAbilities(catId)
    override suspend fun getEnemyCatAbilities(catId: Int): List<Ability> = abilityDao.getEnemyCatAbilities(catId)
}