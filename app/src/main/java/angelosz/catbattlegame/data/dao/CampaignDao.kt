package angelosz.catbattlegame.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import angelosz.catbattlegame.data.entities.Campaign
import angelosz.catbattlegame.data.entities.CampaignChapter
import angelosz.catbattlegame.data.entities.ChapterReward

@Dao
interface CampaignDao {
    /* Campaign */
    @Insert
    suspend fun insertCampaign(campaign: Campaign): Long

    @Update
    suspend fun updateCampaign(campaign: Campaign)

    @Query("Select * from Campaign")
    suspend fun getAllCampaigns(): List<Campaign>
    @Query("Select * from campaign where id = :id")
    suspend fun getCampaignById(id: Long): Campaign

    /* Campaign Chapters */
    @Insert
    suspend fun insertCampaignChapter(campaignChapter: CampaignChapter): Long
    @Update
    suspend fun updateCampaignChapter(campaignChapter: CampaignChapter)

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