package angelosz.catbattlegame.data.repository

import angelosz.catbattlegame.data.dao.PlayerDao
import angelosz.catbattlegame.domain.models.entities.OwnedCat
import angelosz.catbattlegame.domain.models.entities.PlayerAccount

class OfflinePlayerAccountRepository(val dao: PlayerDao): PlayerAccountRepository {
    /* Player Account */
    override suspend fun getPlayerAccount(): PlayerAccount? = dao.getPlayerAccount()
    override suspend fun createOrUpdateAccount(playerAccount: PlayerAccount) = dao.insertOrUpdateAccount(playerAccount)

    /* Owned Cats */
    override suspend fun insertOwnedCat(ownedCat: OwnedCat) = dao.insertOwnedCat(ownedCat)
    override suspend fun updateOwnedCat(ownedCat: OwnedCat) = dao.updateOwnedCat(ownedCat)
    override suspend fun deleteOwnedCat(ownedCat: OwnedCat) = dao.deleteOwnedCat(ownedCat)
    override suspend fun getAllOwnedCats(): List<OwnedCat> = dao.getAllOwnedCats()
    override suspend fun getOwnedCatByCatId(catId: Int): OwnedCat = dao.getOwnedCatByCatId(catId)
}