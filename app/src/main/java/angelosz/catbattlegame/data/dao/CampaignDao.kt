package angelosz.catbattlegame.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import angelosz.catbattlegame.domain.models.entities.Campaign
import angelosz.catbattlegame.domain.models.entities.CampaignChapter
import angelosz.catbattlegame.domain.models.entities.ChapterReward

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
    @Query("Select * from campaign_chapter where id = :id")
    suspend fun getCampaignChapterById(id: Long): CampaignChapter

    /* Chapter Rewards */
    @Insert
    suspend fun insertChapterReward(chapterReward: ChapterReward): Long

    @Query("Select * from chapter_reward where chapterId = :chapterId")
    suspend fun getChapterRewards(chapterId: Long): List<ChapterReward>
}