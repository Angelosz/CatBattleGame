package angelosz.catbattlegame.data.repository

import angelosz.catbattlegame.domain.models.entities.Campaign
import angelosz.catbattlegame.domain.models.entities.CampaignChapter

interface CampaignRepository {
    /* Campaign */
    suspend fun insertCampaign(campaign: Campaign): Long
    suspend fun getAllCampaigns(): List<Campaign>
    suspend fun getCampaignById(id: Long): Campaign

    /* Campaign Chapter */
    suspend fun insertCampaignChapter(campaignChapter: CampaignChapter): Long
    suspend fun getCampaignChaptersByCampaignId(campaignId: Long): List<CampaignChapter>
}