package angelosz.catbattlegame.data.repository

import angelosz.catbattlegame.domain.models.entities.BattleChest
import angelosz.catbattlegame.domain.models.entities.OwnedCat
import angelosz.catbattlegame.domain.models.entities.PlayerAccount

interface PlayerAccountRepository {
    /* Player Account */
    suspend fun getPlayerAccount(): PlayerAccount?
    suspend fun createOrUpdateAccount(playerAccount: PlayerAccount)

    /* Owned Cats */
    suspend fun insertOwnedCat(ownedCat: OwnedCat)
    suspend fun updateOwnedCat(ownedCat: OwnedCat)
    suspend fun deleteOwnedCat(ownedCat: OwnedCat)

    suspend fun getAllOwnedCats(): List<OwnedCat>
    suspend fun getOwnedCatByCatId(catId: Int): OwnedCat

    /* Battle Chests */
    suspend fun insertBattleChest(battleChest: BattleChest)
    suspend fun deleteBattleChest(battleChest: BattleChest)
    suspend fun getAllBattleChests(): List<BattleChest>
}
