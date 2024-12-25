package angelosz.catbattlegame.data.repository

import angelosz.catbattlegame.data.entities.Ability
import angelosz.catbattlegame.data.entities.ChapterEnemy
import angelosz.catbattlegame.data.entities.EnemyAbility
import angelosz.catbattlegame.data.entities.EnemyCat
import angelosz.catbattlegame.ui.campaign.SimplifiedEnemyCatData

interface EnemyCatRepository {
    suspend fun insertEnemyCat(enemyCat: EnemyCat): Long
    suspend fun insertEnemyAbility(enemyAbility: EnemyAbility): Long
    suspend fun insertChapterEnemy(chapterEnemy: ChapterEnemy): Long

    suspend fun getEnemyCatById(id: Long): EnemyCat
    suspend fun getEnemyCatsByCampaignChapterId(campaignChapterId: Long): List<EnemyCat>
    suspend fun getSimplifiedEnemiesByCampaignChapterId(campaignChapterId: Long): List<SimplifiedEnemyCatData>
    suspend fun getEnemyCatAbilities(enemyCatId: Long): List<Ability>
}