package angelosz.catbattlegame.data.repository

import angelosz.catbattlegame.domain.models.entities.Ability
import angelosz.catbattlegame.domain.models.entities.ChapterEnemy
import angelosz.catbattlegame.domain.models.entities.EnemyAbility
import angelosz.catbattlegame.domain.models.entities.EnemyCat
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