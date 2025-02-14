package angelosz.catbattlegame.data.repository

import angelosz.catbattlegame.data.entities.BattleChest

interface BattleChestRepository {
    suspend fun insertBattleChest(battleChest: BattleChest)
    suspend fun deleteBattleChest(battleChest: BattleChest)
    suspend fun getAllBattleChests(): List<BattleChest>
}