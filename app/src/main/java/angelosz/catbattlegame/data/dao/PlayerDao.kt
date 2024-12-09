package angelosz.catbattlegame.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import angelosz.catbattlegame.domain.models.entities.BattleChest
import angelosz.catbattlegame.domain.models.entities.OwnedCat
import angelosz.catbattlegame.domain.models.entities.PlayerAccount
import angelosz.catbattlegame.domain.models.entities.PlayerTeam
import angelosz.catbattlegame.domain.models.entities.PlayerTeamOwnedCat
import angelosz.catbattlegame.ui.teambuilder.BasicCatData

@Dao
interface PlayerDao {
    /* Player Account */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateAccount(playerAccount: PlayerAccount)

    @Query("Select * from player_account Limit 1")
    suspend fun getPlayerAccount(): PlayerAccount?


    /* Owned Cats */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOwnedCat(ownedCat: OwnedCat)
    @Update
    suspend fun updateOwnedCat(ownedCat: OwnedCat)
    @Delete
    suspend fun deleteOwnedCat(ownedCat: OwnedCat)

    @Query("Select * from player_owned_cat")
    suspend fun getAllOwnedCats(): List<OwnedCat>
    @Query("Select * from player_owned_cat where catId = :catId")
    suspend fun getOwnedCatByCatId(catId: Int): OwnedCat
    @Query("Select * from player_owned_cat where catId in (:catIds)")
    suspend fun getOwnedCatsByIds(catIds: List<Int>): List<OwnedCat>
    @Query("Select * from player_owned_cat order by id asc limit :limit offset :offset")
    suspend fun getPaginatedOwnedCats(limit: Int, offset: Int): List<OwnedCat>
    @Query("Select count(catId) from player_owned_cat")
    suspend fun getCount(): Int
    @Query("Select exists(select 1 from player_owned_cat where catId = :catId)")
    suspend fun ownsCat(catId: Int): Boolean

    /* Battle Chests */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBattleChest(battleChest: BattleChest)
    @Delete
    suspend fun deleteBattleChest(battleChest: BattleChest)

    @Query("Select * from battle_chests")
    suspend fun getAllBattleChests(): List<BattleChest>

    /* Player Teams */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlayerTeam(playerTeam: PlayerTeam): Long
    @Insert
    suspend fun addCatToTeam(playerTeamOwnedCat: PlayerTeamOwnedCat)
    @Delete
    suspend fun deleteTeam(playerTeam: PlayerTeam)

    @Query("delete from player_team where id = :teamId")
    suspend fun deleteTeamById(teamId: Long)
    @Query("delete from playerteam_ownedcat where teamId = :teamId")
    suspend fun clearTeam(teamId: Long)

    @Query("Select * from player_team")
    suspend fun getAllPlayerTeams(): List<PlayerTeam>
    @Query("Select * from player_team where id = :id")
    suspend fun getPlayerTeamById(id: Long): PlayerTeam

    @Query("""SELECT 
        poc.id AS catId,
        c.image AS image
            FROM player_team pt
            JOIN playerteam_ownedcat ptoc ON pt.id = ptoc.teamId
            JOIN player_owned_cat poc ON ptoc.ownedCatId = poc.id
            JOIN cats c ON poc.catId = c.id
            WHERE pt.id = :teamId
            ORDER BY ptoc.position"""
    )
    suspend fun getPlayerTeamCats(teamId: Long): List<BasicCatData>
}