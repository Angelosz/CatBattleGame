package angelosz.catbattlegame.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import angelosz.catbattlegame.domain.models.entities.BattleChest

@Dao
interface BattleChestDao {
    /* Battle Chests */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBattleChest(battleChest: BattleChest)
    @Delete
    suspend fun deleteBattleChest(battleChest: BattleChest)
    @Query("Select * from battle_chests")
    suspend fun getAllBattleChests(): List<BattleChest>
}