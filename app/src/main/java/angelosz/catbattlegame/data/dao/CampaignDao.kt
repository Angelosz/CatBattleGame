package angelosz.catbattlegame.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import angelosz.catbattlegame.domain.models.entities.Campaign
import angelosz.catbattlegame.domain.models.entities.CampaignChapter

@Dao
interface CampaignDao {
    /* Campaign */
    @Insert
    suspend fun insertCampaign(campaign: Campaign): Long

    @Query("Select * from Campaign")
    suspend fun getAllCampaigns(): List<Campaign>
    @Query("Select * from campaign where id = :id")
    suspend fun getCampaignById(id: Long): Campaign

    /* Campaign Chapters */
    @Insert
    suspend fun insertCampaignChapter(campaignChapter: CampaignChapter): Long

    @Query("Select * from campaign_chapter where campaignId = :campaignId")
    suspend fun getCampaignChaptersByCampaignId(campaignId: Long): List<CampaignChapter>
}