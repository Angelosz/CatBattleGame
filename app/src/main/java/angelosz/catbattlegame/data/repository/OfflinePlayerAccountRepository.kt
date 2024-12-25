package angelosz.catbattlegame.data.repository

import angelosz.catbattlegame.data.dao.PlayerDao
import angelosz.catbattlegame.domain.models.entities.OwnedCat
import angelosz.catbattlegame.domain.models.entities.PlayerAccount
import angelosz.catbattlegame.domain.models.entities.PlayerTeam
import angelosz.catbattlegame.domain.models.entities.PlayerTeamOwnedCat
import angelosz.catbattlegame.ui.teambuilder.BasicCatData
import kotlinx.coroutines.flow.Flow

class OfflinePlayerAccountRepository(val dao: PlayerDao): PlayerAccountRepository {
    /* Player Account */
    override suspend fun getPlayerAccountAsFlow(): Flow<PlayerAccount?> = dao.getPlayerAccountAsFlow()
    override suspend fun getPlayerAccount(): PlayerAccount? = dao.getPlayerAccount()
    override suspend fun createOrUpdateAccount(playerAccount: PlayerAccount) = dao.insertOrUpdateAccount(playerAccount)

    /* Crystals */
    override suspend fun addCrystals(amount: Int) = dao.addCrystals(amount)
    override suspend fun reduceCrystals(amount: Int) {
        val crystals = dao.getPlayerAccount()?.gold
        if(crystals != null){
            if(amount > crystals){
                dao.reduceCrystals(amount)
            } else {
                dao.reduceCrystals(crystals)
            }
        }

    }

    /* Gold */
    override suspend fun addGold(amount: Int) = dao.addGold(amount)
    override suspend fun reduceGold(amount: Int) {
        val gold = dao.getPlayerAccount()?.gold
        if(gold != null){
            if(amount > gold){
                dao.reduceGold(amount)
            } else {
                dao.reduceGold(gold)
            }
        }
    }

    /* Owned Cats */
    override suspend fun insertOwnedCat(ownedCat: OwnedCat) = dao.insertOwnedCat(ownedCat)
    override suspend fun updateOwnedCat(ownedCat: OwnedCat) = dao.updateOwnedCat(ownedCat)
    override suspend fun deleteOwnedCat(ownedCat: OwnedCat) = dao.deleteOwnedCat(ownedCat)
    override suspend fun getAllOwnedCats(): List<OwnedCat> = dao.getAllOwnedCats()
    override suspend fun getOwnedCatsByCatIds(catIds: List<Int>): List<OwnedCat> = dao.getOwnedCatsByIds(catIds)
    override suspend fun getOwnedCatByCatId(catId: Int): OwnedCat = dao.getOwnedCatByCatId(catId)
    override suspend fun getOwnedCatById(id: Int): OwnedCat = dao.getOwnedCatById(id)
    override suspend fun getPaginatedOwnedCats(limit: Int, offset: Int): List<OwnedCat> = dao.getPaginatedOwnedCats(limit, offset)
    override suspend fun getCount(): Int = dao.getCount()
    override suspend fun ownsCat(catId: Int): Boolean = dao.ownsCat(catId)

    /* Player Teams */
    override suspend fun insertPlayerTeam(playerTeam: PlayerTeam): Long = dao.insertPlayerTeam(playerTeam)
    override suspend fun deleteTeam(playerTeam: PlayerTeam) = dao.deleteTeam(playerTeam)
    override suspend fun clearTeam(teamId: Long) = dao.clearTeam(teamId)
    override suspend fun deleteTeambyId(teamId: Long) = dao.deleteTeamById(teamId)
    override suspend fun getPlayerTeamById(teamId: Long) = dao.getPlayerTeamById(teamId)
    override suspend fun getAllPlayerTeams(): List<PlayerTeam> = dao.getAllPlayerTeams()
    override suspend fun addCatToTeam(playerTeamOwnedCat: PlayerTeamOwnedCat) = dao.addCatToTeam(playerTeamOwnedCat)
    override suspend fun getTeamData(teamId: Long): List<BasicCatData> {
        val catBasicData = dao.getPlayerTeamCats(teamId)
        return catBasicData
    }
}