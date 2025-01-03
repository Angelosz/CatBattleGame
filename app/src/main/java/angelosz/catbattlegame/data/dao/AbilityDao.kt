package angelosz.catbattlegame.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import angelosz.catbattlegame.data.entities.Ability
import angelosz.catbattlegame.data.entities.CatAbilityCrossRef

@Dao
interface AbilityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAbilities(abilities: List<Ability>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCatAbilityCrossRefs(abilities: List<CatAbilityCrossRef>)

    @Query("Select * from abilities where abilitySource = 'PLAYER'")
    suspend fun getAllPlayerAbilities(): List<Ability>

    @Query("Select * from abilities where id = :id")
    suspend fun getAbilityById(id: Int): Ability

    @Query("Select abilities.* from abilities" +
            " INNER JOIN cat_ability_crossref" +
            " ON abilities.id == cat_ability_crossref.abilityId" +
            " WHERE cat_ability_crossref.catId == :catId")
    suspend fun getCatAbilities(catId: Int): List<Ability>

    @Query("Select abilities.* from abilities" +
            " INNER JOIN campaign_enemy_ability" +
            " ON abilities.id == campaign_enemy_ability.abilityId" +
            " WHERE campaign_enemy_ability.enemyCatId == :catId")
    suspend fun getEnemyCatAbilities(catId: Int): List<Ability>
}