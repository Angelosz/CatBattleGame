package angelosz.catbattlegame.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import angelosz.catbattlegame.domain.models.entities.Ability
import angelosz.catbattlegame.domain.models.entities.CatAbilityCrossRef

@Dao
interface AbilityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAbilities(abilities: List<Ability>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCatAbilityCrossRefs(abilities: List<CatAbilityCrossRef>)

    @Query("Select * from abilities")
    suspend fun getAllAbilities(): List<Ability>

    @Query("Select * from abilities where id = :id")
    suspend fun getAbilityById(id: Int): Ability

    @Query("Select * from abilities" +
            " INNER JOIN cat_ability_crossref" +
            " ON abilities.id == cat_ability_crossref.abilityId" +
            " WHERE cat_ability_crossref.catId == :catId")
    suspend fun getCatAbilities(catId: Int): List<Ability>
}