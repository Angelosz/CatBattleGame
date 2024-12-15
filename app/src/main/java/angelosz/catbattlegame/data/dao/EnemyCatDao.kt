package angelosz.catbattlegame.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import angelosz.catbattlegame.domain.models.entities.Ability
import angelosz.catbattlegame.domain.models.entities.ChapterEnemy
import angelosz.catbattlegame.domain.models.entities.EnemyAbility
import angelosz.catbattlegame.domain.models.entities.EnemyCat
import angelosz.catbattlegame.ui.combat.SimplifiedEnemyCatData

@Dao
interface EnemyCatDao {
    @Insert
    suspend fun insertEnemyCat(enemyCat: EnemyCat): Long
    @Insert
    suspend fun insertEnemyAbility(enemyAbility: EnemyAbility): Long
    @Insert
    suspend fun insertChapterEnemy(chapterEnemy: ChapterEnemy): Long

    @Query("SELECT * FROM campaign_enemies WHERE id = :id")
    suspend fun getEnemyCatById(id: Long): EnemyCat

    @Query("""
        SELECT *
        FROM campaign_enemies E
        JOIN chapter_enemy CE ON E.id = CE.enemyCatId
        WHERE CE.chapterId = :campaignChapterId
        ORDER BY CE.`order` ASC
    """)
    suspend fun getEnemyCatsByCampaignChapterId(campaignChapterId: Long): List<EnemyCat>

    @Query("""
        SELECT E.id, E.name, E.image, E.description
        FROM campaign_enemies E
        JOIN chapter_enemy CE ON E.id = CE.enemyCatId
        WHERE CE.chapterId = :campaignChapterId
        ORDER BY CE.`order` ASC
    """)
    suspend fun getSimplifiedEnemiesByCampaignChapterId(campaignChapterId: Long): List<SimplifiedEnemyCatData>

    @Query("SELECT * FROM abilities WHERE id IN (SELECT abilityId FROM campaign_enemy_ability WHERE enemyCatId = :enemyCatId)")
    suspend fun getEnemyCatAbilities(enemyCatId: Long): List<Ability>
}

