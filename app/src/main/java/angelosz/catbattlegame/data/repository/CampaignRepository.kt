package angelosz.catbattlegame.data.repository

import angelosz.catbattlegame.data.entities.CampaignChapter
import angelosz.catbattlegame.data.entities.CampaignEntity
import angelosz.catbattlegame.data.entities.ChapterReward
import angelosz.catbattlegame.domain.enums.CampaignState
import angelosz.catbattlegame.ui.campaign.data.Campaign
import angelosz.catbattlegame.ui.campaign.data.Chapter

interface CampaignRepository {
    /* Campaign */
    suspend fun insertCampaign(campaign: CampaignEntity): Long
    suspend fun insertCampaigns(campaigns: List<CampaignEntity>)
    suspend fun updateCampaignState(campaignId: Long, state: CampaignState)
    suspend fun getAllCampaigns(): List<Campaign>
    suspend fun getCampaignById(id: Long): Campaign
    suspend fun getCampaignState(campaignId: Long): CampaignState


    /* Campaign Chapter */
    suspend fun insertCampaignChapter(campaignChapter: CampaignChapter): Long
    suspend fun insertCampaignChapters(campaignChapters: List<CampaignChapter>)
    suspend fun updateCampaignChapter(campaignChapter: CampaignChapter)
    suspend fun getCampaignChaptersByCampaignId(campaignId: Long): List<Chapter>
    suspend fun getChapterById(id: Long): Chapter

    suspend fun getChapterState(chapterId: Long): CampaignState
    suspend fun updateChapterState(chapterId: Long, state: CampaignState)

    /* Chapter Rewards */
    suspend fun insertChapterReward(chapterReward: ChapterReward): Long
    suspend fun insertChapterRewards(chapterRewards: List<ChapterReward>)
    suspend fun getChapterRewards(chapterId: Long): List<ChapterReward>

}