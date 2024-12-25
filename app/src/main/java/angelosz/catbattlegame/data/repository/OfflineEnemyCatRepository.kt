package angelosz.catbattlegame.data.repository

import angelosz.catbattlegame.data.dao.EnemyCatDao
import angelosz.catbattlegame.data.entities.Ability
import angelosz.catbattlegame.data.entities.ChapterEnemy
import angelosz.catbattlegame.data.entities.EnemyAbility
import angelosz.catbattlegame.data.entities.EnemyCat
import angelosz.catbattlegame.ui.campaign.SimplifiedEnemyCatData

class OfflineEnemyCatRepository(val dao: EnemyCatDao): EnemyCatRepository {
    override suspend fun insertEnemyCat(enemyCat: EnemyCat): Long = dao.insertEnemyCat(enemyCat)
    override suspend fun insertEnemyAbility(enemyAbility: EnemyAbility): Long = dao.insertEnemyAbility(enemyAbility)
    override suspend fun insertChapterEnemy(chapterEnemy: ChapterEnemy): Long = dao.insertChapterEnemy(chapterEnemy)
    override suspend fun getEnemyCatById(id: Long): EnemyCat = dao.getEnemyCatById(id)
    override suspend fun getEnemyCatsByCampaignChapterId(campaignChapterId: Long): List<EnemyCat> = dao.getEnemyCatsByCampaignChapterId(campaignChapterId)
    override suspend fun getSimplifiedEnemiesByCampaignChapterId(campaignChapterId: Long): List<SimplifiedEnemyCatData> = dao.getSimplifiedEnemiesByCampaignChapterId(campaignChapterId)
    override suspend fun getEnemyCatAbilities(enemyCatId: Long): List<Ability> = dao.getEnemyCatAbilities(enemyCatId)
}