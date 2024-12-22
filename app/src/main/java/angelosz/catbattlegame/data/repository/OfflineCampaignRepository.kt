package angelosz.catbattlegame.data.repository

import angelosz.catbattlegame.data.dao.CampaignDao
import angelosz.catbattlegame.domain.models.entities.Campaign
import angelosz.catbattlegame.domain.models.entities.CampaignChapter
import angelosz.catbattlegame.domain.models.entities.ChapterReward

class OfflineCampaignRepository(val dao: CampaignDao): CampaignRepository {
    /* Campaign */
    override suspend fun insertCampaign(campaign: Campaign): Long = dao.insertCampaign(campaign)
    override suspend fun getAllCampaigns(): List<Campaign> = dao.getAllCampaigns()
    override suspend fun getCampaignById(id: Long): Campaign = dao.getCampaignById(id)

    /* Campaign Chapters */
    override suspend fun insertCampaignChapter(campaignChapter: CampaignChapter): Long = dao.insertCampaignChapter(campaignChapter)
    override suspend fun getCampaignChaptersByCampaignId(campaignId: Long): List<CampaignChapter> = dao.getCampaignChaptersByCampaignId(campaignId)
    override suspend fun getChapterById(id: Long): CampaignChapter = dao.getCampaignChapterById(id)

    /* Chapter Rewards */
    override suspend fun insertChapterReward(chapterReward: ChapterReward): Long = dao.insertChapterReward(chapterReward)
    override suspend fun getChapterRewards(chapterId: Long): List<ChapterReward> = dao.getChapterRewards(chapterId)
}