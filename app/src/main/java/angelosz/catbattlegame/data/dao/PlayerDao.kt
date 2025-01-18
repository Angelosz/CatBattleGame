package angelosz.catbattlegame.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import angelosz.catbattlegame.data.entities.EnemyDiscoveryState
import angelosz.catbattlegame.data.entities.OwnedCat
import angelosz.catbattlegame.data.entities.PlayerAccount
import angelosz.catbattlegame.data.entities.PlayerTeam
import angelosz.catbattlegame.data.entities.PlayerTeamOwnedCat
import angelosz.catbattlegame.data.entities.notifications.BattleChestNotificationEntity
import angelosz.catbattlegame.data.entities.notifications.CatNotificationEntity
import angelosz.catbattlegame.data.entities.notifications.CurrencyNotificationEntity
import angelosz.catbattlegame.data.entities.notifications.NotificationsEntity
import angelosz.catbattlegame.ui.armory.data.SimpleArmoryCatData
import angelosz.catbattlegame.ui.combat.BasicCatData
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayerDao {
    /* Player Account */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlayerAccount(playerAccount: PlayerAccount)
    @Update
    suspend fun updateAccount(playerAccount: PlayerAccount)
    @Query("Select * from player_account Limit 1")
    fun getPlayerAccountAsFlow(): Flow<PlayerAccount?>
    @Query("Select * from player_account Limit 1")
    suspend fun getPlayerAccount(): PlayerAccount?
    @Query("Select gameVersion from player_account Limit 1")
    suspend fun getGameVersion(): Int

    /* Crystals */
    @Query("Select crystals from player_account Limit 1")
    suspend fun getCrystalsAmount(): Int
    @Query("Update player_account set crystals = crystals + :amount")
    suspend fun addCrystals(amount: Int)
    @Query("Update player_account set crystals = crystals - :amount")
    suspend fun reduceCrystals(amount: Int)

    /* Gold */
    @Query("Select gold from player_account Limit 1")
    suspend fun getGoldAmount(): Int
    @Query("Update player_account set gold = gold + :amount")
    suspend fun addGold(amount: Int)
    @Query("Update player_account set gold = gold - :amount")
    suspend fun reduceGold(amount: Int)


    /* Owned Cats */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOwnedCat(ownedCat: OwnedCat)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOwnedCats(ownedCats: List<OwnedCat>)
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
    @Query("Select * from player_owned_cat order by catId asc limit :limit offset :offset")
    suspend fun getPaginatedOwnedCats(limit: Int, offset: Int): List<OwnedCat>
    @Query("Select count(catId) from player_owned_cat")
    suspend fun getCount(): Int
    @Query("Select exists(select 1 from player_owned_cat where catId = :catId)")
    suspend fun ownsCat(catId: Int): Boolean

    @Query("""SELECT
                cats.id as id,
                cats.image,
                player_owned_cat.level,
                player_owned_cat.experience
            FROM 
                player_owned_cat
           JOIN 
                cats ON cats.id = player_owned_cat.catId
            ORDER BY 
                cats.id DESC
            LIMIT 
                :limit OFFSET :offset;""")
    suspend fun getSimpleCatsDataWithExperiencePage(limit: Int, offset: Int): List<SimpleArmoryCatData>

    @Query("""SELECT 
        poc.catId AS id,
        c.image AS image,
        poc.experience AS experience,
        poc.level AS level
            FROM player_team pt
            JOIN playerteam_ownedcat ptoc ON pt.id = ptoc.teamId
            JOIN player_owned_cat poc ON ptoc.ownedCatId = poc.catId
            JOIN cats c ON poc.catId = c.id
            WHERE pt.id = :teamId
    """)
    suspend fun getSimpleArmoryCatsDataFromTeam(teamId: Long): List<SimpleArmoryCatData>

    /* Player Teams */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlayerTeam(playerTeam: PlayerTeam): Long
    @Update
    suspend fun updatePlayerTeam(playerTeam: PlayerTeam)
    @Insert
    suspend fun addCatToTeam(playerTeamOwnedCat: PlayerTeamOwnedCat)
    @Delete
    suspend fun deleteTeam(playerTeam: PlayerTeam)
    @Query("Select exists(select 1 from player_team where id = :teamId)")
    suspend fun teamExists(teamId: Long): Boolean

    @Query("delete from player_team where id = :teamId")
    suspend fun deleteTeamById(teamId: Long)
    @Query("delete from playerteam_ownedcat where teamId = :teamId")
    suspend fun clearTeam(teamId: Long)
    @Query("Select * from player_team")
    suspend fun getAllPlayerTeams(): List<PlayerTeam>
    @Query("Select * from player_team where id = :id")
    suspend fun getPlayerTeamById(id: Long): PlayerTeam

    @Query("""SELECT 
        poc.catId AS catId,
        c.image AS image
            FROM player_team pt
            JOIN playerteam_ownedcat ptoc ON pt.id = ptoc.teamId
            JOIN player_owned_cat poc ON ptoc.ownedCatId = poc.catId
            JOIN cats c ON poc.catId = c.id
            WHERE pt.id = :teamId
            ORDER BY ptoc.position"""
    )
    suspend fun getPlayerTeamCats(teamId: Long): List<BasicCatData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun discoverEnemies(enemies: List<EnemyDiscoveryState>)

    /* Notifications */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotification(notification: NotificationsEntity): Long
    @Query("Select * from notifications")
    suspend fun getAllNotifications(): List<NotificationsEntity>
    @Query("Delete from notifications where notificationId = :id")
    suspend fun deleteNotification(id: Long)
    @Query("SELECT * from notifications")
    fun getAllNotificationsFlow(): Flow<List<NotificationsEntity>>

    /* Cat Notifications */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCatNotification(notification: CatNotificationEntity): Long
    @Query("Delete from cat_notifications where id = :id")
    suspend fun deleteCatNotification(id: Long)
    @Query("SELECT * from cat_notifications where id = :notificationId")
    suspend fun getCatNotification(notificationId: Long): CatNotificationEntity

    /* Battle Chest Notifications */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBattleChestNotification(notification: BattleChestNotificationEntity): Long
    @Query("Delete from battle_chest_notifications where id = :id")
    suspend fun deleteBattleChestNotification(id: Long)
    @Query("SELECT * from battle_chest_notifications where id = :notificationId")
    suspend fun getBattleChestNotification(notificationId: Long): BattleChestNotificationEntity

    /* Currency Notifications */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrencyNotification(notification: CurrencyNotificationEntity): Long
    @Query("Delete from currency_notifications where id = :id")
    suspend fun deleteCurrencyNotification(id: Long)
    @Query("SELECT * from currency_notifications where id = :notificationId")
    suspend fun getCurrencyNotification(notificationId: Long): CurrencyNotificationEntity

}