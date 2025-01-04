package angelosz.catbattlegame.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import angelosz.catbattlegame.data.entities.Ability
import angelosz.catbattlegame.data.entities.ChapterEnemy
import angelosz.catbattlegame.data.entities.EnemyAbility
import angelosz.catbattlegame.data.entities.EnemyCat
import angelosz.catbattlegame.ui.archives.data.SimpleArchiveEnemyData
import angelosz.catbattlegame.ui.campaign.SimplifiedEnemyCatData

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
        SELECT 
            E.id, 
            E.name, 
            E.description, 
            E.image, 
            E.armorType, 
            E.role, 
            E.baseHealth, 
            E.baseAttack, 
            E.baseDefense, 
            E.attackSpeed, 
            E.rarity, 
            E.enemyType
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

    @Query("Select * from chapter_enemy where chapterId = :chapterId")
    suspend fun getChapterEnemies(chapterId: Long): List<ChapterEnemy>

    @Query("""SELECT
            E.id, 
            E.image, 
            (SELECT state from enemy_discovery where enemyCatId = E.id) as isDiscovered
            FROM campaign_enemies E
            ORDER BY E.id ASC
            LIMIT :limit OFFSET :offset
    """)
    suspend fun getSimplifiedArchiveEnemiesPage(limit: Int, offset: Int): List<SimpleArchiveEnemyData>

    @Query("SELECT COUNT(*) FROM campaign_enemies")
    suspend fun getCount(): Int

    @Query("SELECT state from enemy_discovery where enemyCatId = :enemyCatId")
    suspend fun getEnemyCatDiscoveryStatus(enemyCatId: Long): Boolean
}

