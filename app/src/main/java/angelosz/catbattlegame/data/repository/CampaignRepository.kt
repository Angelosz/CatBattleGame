package angelosz.catbattlegame.data.repository

import angelosz.catbattlegame.domain.models.entities.Campaign
import angelosz.catbattlegame.domain.models.entities.CampaignChapter
import angelosz.catbattlegame.domain.models.entities.ChapterReward

interface CampaignRepository {
    /* Campaign */
    suspend fun insertCampaign(campaign: Campaign): Long
    suspend fun updateCampaign(campaign: Campaign)
    suspend fun getAllCampaigns(): List<Campaign>
    suspend fun getCampaignById(id: Long): Campaign


    /* Campaign Chapter */
    suspend fun insertCampaignChapter(campaignChapter: CampaignChapter): Long
    suspend fun updateCampaignChapter(campaignChapter: CampaignChapter)
    suspend fun getCampaignChaptersByCampaignId(campaignId: Long): List<CampaignChapter>
    suspend fun getChapterById(id: Long): CampaignChapter

    /* Chapter Rewards */
    suspend fun insertChapterReward(chapterReward: ChapterReward): Long
    suspend fun getChapterRewards(chapterId: Long): List<ChapterReward>

}