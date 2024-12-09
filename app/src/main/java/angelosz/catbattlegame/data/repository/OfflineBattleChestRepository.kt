package angelosz.catbattlegame.data.repository

import angelosz.catbattlegame.data.dao.BattleChestDao
import angelosz.catbattlegame.domain.models.entities.BattleChest

class OfflineBattleChestRepository(val dao: BattleChestDao): BattleChestRepository {
    override suspend fun insertBattleChest(battleChest: BattleChest) = dao.insertBattleChest(battleChest)
    override suspend fun deleteBattleChest(battleChest: BattleChest) = dao.deleteBattleChest(battleChest)
    override suspend fun getAllBattleChests(): List<BattleChest> = dao.getAllBattleChests()
}