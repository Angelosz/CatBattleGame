package angelosz.catbattlegame.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import angelosz.catbattlegame.domain.models.Cat

@Dao
interface CatDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCats(cats: List<Cat>)

    @Query("Select * from cats")
    suspend fun getAllCats(): List<Cat>

    @Query("Select * from cats where id = :id")
    suspend fun getCatById(id: Int): Cat
}