package angelosz.catbattlegame.data.repository

import angelosz.catbattlegame.data.dao.PlayerDao
import angelosz.catbattlegame.domain.models.entities.BattleChest
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
    override suspend fun getOwnedCatsByCatIds(catIds: List<Int>): List<OwnedCat> = dao.getOwnedCatsByIds(catIds)
    override suspend fun getOwnedCatByCatId(catId: Int): OwnedCat = dao.getOwnedCatByCatId(catId)
    override suspend fun getPaginatedOwnedCats(limit: Int, offset: Int): List<OwnedCat> = dao.getPaginatedOwnedCats(limit, offset)
    override suspend fun getCount(): Int = dao.getCount()
    override suspend fun ownsCat(catId: Int): Boolean = dao.ownsCat(catId)

    /* Battle Chests */
    override suspend fun insertBattleChest(battleChest: BattleChest) = dao.insertBattleChest(battleChest)
    override suspend fun deleteBattleChest(battleChest: BattleChest) = dao.deleteBattleChest(battleChest)
    override suspend fun getAllBattleChests(): List<BattleChest> = dao.getAllBattleChests()
}