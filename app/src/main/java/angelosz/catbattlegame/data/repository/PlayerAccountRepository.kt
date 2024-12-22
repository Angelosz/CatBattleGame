package angelosz.catbattlegame.data.repository

import angelosz.catbattlegame.domain.models.entities.OwnedCat
import angelosz.catbattlegame.domain.models.entities.PlayerAccount
import angelosz.catbattlegame.domain.models.entities.PlayerTeam
import angelosz.catbattlegame.domain.models.entities.PlayerTeamOwnedCat
import angelosz.catbattlegame.ui.teambuilder.BasicCatData
import kotlinx.coroutines.flow.Flow

interface PlayerAccountRepository {
    /* Player Account */
    suspend fun getPlayerAccountAsFlow(): Flow<PlayerAccount?>
    suspend fun getPlayerAccount(): PlayerAccount?
    suspend fun createOrUpdateAccount(playerAccount: PlayerAccount)

    /* Crystals */
    suspend fun addCrystals(amount: Int)
    suspend fun reduceCrystals(amount: Int)

    /* Owned Cats */
    suspend fun insertOwnedCat(ownedCat: OwnedCat)
    suspend fun updateOwnedCat(ownedCat: OwnedCat)
    suspend fun deleteOwnedCat(ownedCat: OwnedCat)

    suspend fun getAllOwnedCats(): List<OwnedCat>
    suspend fun getOwnedCatByCatId(catId: Int): OwnedCat
    suspend fun getOwnedCatById(id: Int): OwnedCat
    suspend fun getOwnedCatsByCatIds(catIds: List<Int>): List<OwnedCat>
    suspend fun getPaginatedOwnedCats(limit: Int, offset: Int): List<OwnedCat>
    suspend fun getCount(): Int
    suspend fun ownsCat(catId: Int): Boolean

    /* Player Teams */
    suspend fun insertPlayerTeam(playerTeam: PlayerTeam): Long
    suspend fun deleteTeam(playerTeam: PlayerTeam)
    suspend fun deleteTeambyId(teamId: Long)
    suspend fun clearTeam(teamId: Long)
    suspend fun getPlayerTeamById(teamId: Long): PlayerTeam
    suspend fun getAllPlayerTeams(): List<PlayerTeam>
    suspend fun addCatToTeam(playerTeamOwnedCat: PlayerTeamOwnedCat)
    suspend fun getTeamData(teamId: Long): List<BasicCatData>
}
