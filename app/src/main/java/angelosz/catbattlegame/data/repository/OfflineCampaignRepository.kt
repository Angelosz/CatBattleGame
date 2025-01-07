package angelosz.catbattlegame.data.repository

import angelosz.catbattlegame.data.dao.CampaignDao
import angelosz.catbattlegame.data.entities.CampaignChapter
import angelosz.catbattlegame.data.entities.CampaignChapterState
import angelosz.catbattlegame.data.entities.CampaignCompletionState
import angelosz.catbattlegame.data.entities.CampaignEntity
import angelosz.catbattlegame.data.entities.ChapterReward
import angelosz.catbattlegame.domain.enums.CampaignState
import angelosz.catbattlegame.ui.campaign.data.Campaign
import angelosz.catbattlegame.ui.campaign.data.Chapter

class OfflineCampaignRepository(val dao: CampaignDao): CampaignRepository {
    /* Campaign */
    override suspend fun insertCampaign(campaign: CampaignEntity): Long = dao.insertCampaign(campaign)

    override suspend fun insertCampaigns(campaigns: List<CampaignEntity>) = dao.insertCampaigns(campaigns)
    override suspend fun getAllCampaigns(): List<Campaign> {
        val campaigns = dao.getAllCampaigns()
        return campaigns.map { campaign ->
            val campaignState = dao.getCampaignState(campaign.id) ?: CampaignState.LOCKED
            Campaign(
                id = campaign.id,
                state = campaignState,
                name = campaign.name,
                description = campaign.description,
                image = campaign.image,
                nextCampaign = campaign.nextCampaign
            )
        }
    }
    override suspend fun getCampaignById(id: Long): Campaign {
        val campaign = dao.getCampaignById(id)
        val campaignState = dao.getCampaignState(campaign.id) ?: CampaignState.LOCKED
        return Campaign(
            id = campaign.id,
            state = campaignState,
            name = campaign.name,
            description = campaign.description,
            image = campaign.image,
            nextCampaign = campaign.nextCampaign
        )
    }

    override suspend fun getCampaignState(campaignId: Long): CampaignState = dao.getCampaignState(campaignId)
    override suspend fun updateCampaignState(campaignId: Long, state: CampaignState) = dao.updateCampaignState(
        CampaignCompletionState(campaignId, state)
    )

    /* Campaign Chapters */
    override suspend fun insertCampaignChapter(campaignChapter: CampaignChapter): Long = dao.insertCampaignChapter(campaignChapter)
    override suspend fun insertCampaignChapters(campaignChapters: List<CampaignChapter>) = dao.insertCampaignChapters(campaignChapters)
    override suspend fun updateCampaignChapter(campaignChapter: CampaignChapter) = dao.updateCampaignChapter(campaignChapter)
    override suspend fun getCampaignChaptersByCampaignId(campaignId: Long): List<Chapter> {
        val chapters = dao.getCampaignChaptersByCampaignId(campaignId)
        return chapters.map{ chapter ->
            val chapterState = dao.getChapterState(chapter.id) ?: CampaignState.LOCKED
            Chapter(
                id = chapter.id,
                campaignId = chapter.campaignId,
                order = chapter.order,
                state = chapterState,
                experience = chapter.experience,
                name = chapter.name,
                description = chapter.description,
                image = chapter.image,
                unlocksChapter = chapter.unlocksChapter,
                isLastCampaignChapter = chapter.isLastCampaignChapter
            )
        }
    }
    override suspend fun getChapterById(id: Long): Chapter {
        val chapter = dao.getCampaignChapterById(id)
        val chapterState = dao.getChapterState(chapter.id) ?: CampaignState.LOCKED
        return Chapter(
            id = chapter.id,
            campaignId = chapter.campaignId,
            order = chapter.order,
            state = chapterState,
            experience = chapter.experience,
            name = chapter.name,
            description = chapter.description,
            image = chapter.image,
            unlocksChapter = chapter.unlocksChapter,
            isLastCampaignChapter = chapter.isLastCampaignChapter
        )
    }

    override suspend fun getChapterState(chapterId: Long): CampaignState = dao.getChapterState(chapterId)
    override suspend fun updateChapterState(chapterId: Long, state: CampaignState) = dao.updateChapterState(
        CampaignChapterState(chapterId, state)
    )

    /* Chapter Rewards */
    override suspend fun insertChapterReward(chapterReward: ChapterReward): Long = dao.insertChapterReward(chapterReward)
    override suspend fun insertChapterRewards(chapterRewards: List<ChapterReward>) = dao.insertChapterRewards(chapterRewards)
    override suspend fun getChapterRewards(chapterId: Long): List<ChapterReward> = dao.getChapterRewards(chapterId)
}