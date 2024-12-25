package angelosz.catbattlegame.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import angelosz.catbattlegame.domain.enums.CatRarity
import angelosz.catbattlegame.data.entities.Cat

@Dao
interface CatDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCats(cats: List<Cat>)

    @Query("Select * from cats")
    suspend fun getAllCats(): List<Cat>

    @Query("Select * from cats where id = :id")
    suspend fun getCatById(id: Int): Cat

    @Query("Select * from cats where id in (:ids)")
    suspend fun getCatsById(ids: List<Int>): List<Cat>

    @Query("Select * from cats where rarity = :rarity order by RANDOM() LIMIT 1")
    suspend fun getRandomCatByRarity(rarity: CatRarity): Cat

    @Query("Select id from cats where rarity = :rarity AND id NOT IN (Select catId from player_owned_cat)")
    suspend fun getUnownedCatsOfRarityIds(rarity: CatRarity): List<Int>
}