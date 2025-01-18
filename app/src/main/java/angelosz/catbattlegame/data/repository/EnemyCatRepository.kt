package angelosz.catbattlegame.data.repository

import angelosz.catbattlegame.data.entities.Ability
import angelosz.catbattlegame.data.entities.ChapterEnemy
import angelosz.catbattlegame.data.entities.EnemyAbility
import angelosz.catbattlegame.data.entities.EnemyCat
import angelosz.catbattlegame.ui.archives.data.ArchiveEnemyData
import angelosz.catbattlegame.ui.archives.data.SimpleArchiveEnemyData
import angelosz.catbattlegame.ui.campaign.SimplifiedEnemyCatData

interface EnemyCatRepository {
    suspend fun insertEnemyCat(enemyCat: EnemyCat): Long
    suspend fun insertEnemyCats(enemyCats: List<EnemyCat>)
    suspend fun clearEnemyCatsTable()
    suspend fun insertEnemyAbility(enemyAbility: EnemyAbility): Long
    suspend fun insertEnemyAbilities(enemyAbilities: List<EnemyAbility>)
    suspend fun clearEnemyAbilitiesTable()
    suspend fun insertChapterEnemy(chapterEnemy: ChapterEnemy): Long
    suspend fun insertChapterEnemies(chapterEnemies: List<ChapterEnemy>)
    suspend fun clearChapterEnemiesTable()

    suspend fun getEnemyCatById(id: Long): EnemyCat
    suspend fun getEnemyCatsByCampaignChapterId(campaignChapterId: Long): List<EnemyCat>
    suspend fun getSimplifiedEnemiesByCampaignChapterId(campaignChapterId: Long): List<SimplifiedEnemyCatData>
    suspend fun getEnemyCatAbilities(enemyCatId: Long): List<Ability>

    suspend fun getSimplifiedArchiveEnemiesPage(limit: Int, offset: Int): List<SimpleArchiveEnemyData>
    suspend fun getArchiveEnemyData(enemyCatId: Long): ArchiveEnemyData
    suspend fun getCount(): Int
}