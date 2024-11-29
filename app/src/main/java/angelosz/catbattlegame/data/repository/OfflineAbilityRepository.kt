package angelosz.catbattlegame.data.repository

import angelosz.catbattlegame.data.dao.AbilityDao
import angelosz.catbattlegame.domain.models.entities.Ability

class OfflineAbilityRepository(private val abilityDao: AbilityDao): AbilityRepository {
    override suspend fun getAllAbilities() = abilityDao.getAllAbilities()

    override suspend fun getAbilityById(id: Int) = abilityDao.getAbilityById(id)

    override suspend fun insertAbilities(abilities: List<Ability>) = abilityDao.insertAbilities(abilities)

    override suspend fun getCatAbilities(catId: Int) = abilityDao.getCatAbilities(catId)
}