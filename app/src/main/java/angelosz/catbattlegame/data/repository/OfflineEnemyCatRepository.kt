package angelosz.catbattlegame.data.repository

import angelosz.catbattlegame.data.dao.EnemyCatDao
import angelosz.catbattlegame.data.entities.Ability
import angelosz.catbattlegame.data.entities.ChapterEnemy
import angelosz.catbattlegame.data.entities.EnemyAbility
import angelosz.catbattlegame.data.entities.EnemyCat
import angelosz.catbattlegame.ui.archives.data.ArchiveEnemyData
import angelosz.catbattlegame.ui.archives.data.SimpleArchiveEnemyData
import angelosz.catbattlegame.ui.campaign.SimplifiedEnemyCatData

class OfflineEnemyCatRepository(val dao: EnemyCatDao): EnemyCatRepository {
    override suspend fun insertEnemyCat(enemyCat: EnemyCat): Long = dao.insertEnemyCat(enemyCat)
    override suspend fun insertEnemyCats(enemyCats: List<EnemyCat>) = dao.insertEnemyCats(enemyCats)
    override suspend fun insertEnemyAbility(enemyAbility: EnemyAbility): Long = dao.insertEnemyAbility(enemyAbility)
    override suspend fun insertEnemyAbilities(enemyAbilities: List<EnemyAbility>) = dao.insertEnemyAbilities(enemyAbilities)
    override suspend fun insertChapterEnemy(chapterEnemy: ChapterEnemy): Long = dao.insertChapterEnemy(chapterEnemy)
    override suspend fun insertChapterEnemies(chapterEnemies: List<ChapterEnemy>) = dao.insertChapterEnemies(chapterEnemies)
    override suspend fun getEnemyCatById(id: Long): EnemyCat = dao.getEnemyCatById(id)
    override suspend fun getEnemyCatsByCampaignChapterId(campaignChapterId: Long): List<EnemyCat> = dao.getEnemyCatsByCampaignChapterId(campaignChapterId)
    override suspend fun getSimplifiedEnemiesByCampaignChapterId(campaignChapterId: Long): List<SimplifiedEnemyCatData> = dao.getSimplifiedEnemiesByCampaignChapterId(campaignChapterId)
    override suspend fun getEnemyCatAbilities(enemyCatId: Long): List<Ability> = dao.getEnemyCatAbilities(enemyCatId)

    override suspend fun getSimplifiedArchiveEnemiesPage(
        limit: Int,
        offset: Int,
    ): List<SimpleArchiveEnemyData> = dao.getSimplifiedArchiveEnemiesPage(limit, offset)

    override suspend fun getArchiveEnemyData(enemyCatId: Long): ArchiveEnemyData {
        val enemy = dao.getEnemyCatById(enemyCatId)
        val abilities = dao.getEnemyCatAbilities(enemyCatId)
        val discoveryStatus = dao.getEnemyCatDiscoveryStatus(enemyCatId)

        return ArchiveEnemyData(
            id = enemy.id,
            name = enemy.name,
            description = enemy.description,
            image = enemy.image,
            armorType = enemy.armorType,
            baseHealth = enemy.baseHealth,
            baseAttack = enemy.baseAttack,
            baseDefense = enemy.baseDefense,
            attackSpeed = enemy.attackSpeed,
            enemyType = enemy.enemyType,
            abilities = abilities,
            isDiscovered = discoveryStatus
        )
    }

    override suspend fun getCount(): Int = dao.getCount()
}