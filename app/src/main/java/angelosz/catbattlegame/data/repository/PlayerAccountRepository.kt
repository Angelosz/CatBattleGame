package angelosz.catbattlegame.data.repository

import angelosz.catbattlegame.data.entities.EnemyDiscoveryState
import angelosz.catbattlegame.data.entities.OwnedCat
import angelosz.catbattlegame.data.entities.PlayerAccount
import angelosz.catbattlegame.data.entities.PlayerTeam
import angelosz.catbattlegame.data.entities.PlayerTeamOwnedCat
import angelosz.catbattlegame.domain.models.BasicCatData
import angelosz.catbattlegame.ui.armory.data.SimpleArmoryCatData
import kotlinx.coroutines.flow.Flow

interface PlayerAccountRepository {
    /* Player Account */
    suspend fun getPlayerAccountAsFlow(): Flow<PlayerAccount?>
    suspend fun getPlayerAccount(): PlayerAccount?
    suspend fun createOrUpdateAccount(playerAccount: PlayerAccount)

    /* Crystals */
    suspend fun addCrystals(amount: Int)
    suspend fun reduceCrystals(amount: Int)

    /* Gold */
    suspend fun addGold(amount: Int)
    suspend fun reduceGold(amount: Int)

    /* Owned Cats */
    suspend fun insertOwnedCat(ownedCat: OwnedCat)
    suspend fun updateOwnedCat(ownedCat: OwnedCat)
    suspend fun deleteOwnedCat(ownedCat: OwnedCat)

    suspend fun getAllOwnedCats(): List<OwnedCat>
    suspend fun getOwnedCatByCatId(catId: Int): OwnedCat
    suspend fun getOwnedCatsByCatIds(catIds: List<Int>): List<OwnedCat>
    suspend fun getPaginatedOwnedCats(limit: Int, offset: Int): List<OwnedCat>
    suspend fun getOwnedCatsCount(): Int
    suspend fun ownsCat(catId: Int): Boolean

    suspend fun getSimpleArmoryCatsDataPage(limit: Int, offset: Int): List<SimpleArmoryCatData>
    suspend fun getSimpleCatsDataFromTeam(teamId: Long): List<SimpleArmoryCatData>

    /* Player Teams */
    suspend fun insertPlayerTeam(playerTeam: PlayerTeam): Long
    suspend fun updatePlayerTeam(playerTeam: PlayerTeam)
    suspend fun deleteTeam(playerTeam: PlayerTeam)
    suspend fun teamExists(teamId: Long): Boolean
    suspend fun deleteTeamById(teamId: Long)
    suspend fun clearTeam(teamId: Long)
    suspend fun getPlayerTeamById(teamId: Long): PlayerTeam
    suspend fun getAllPlayerTeams(): List<PlayerTeam>
    suspend fun addCatToTeam(playerTeamOwnedCat: PlayerTeamOwnedCat)
    suspend fun getTeamData(teamId: Long): List<BasicCatData>
    suspend fun discoverEnemies(enemies: List<EnemyDiscoveryState>)
}
