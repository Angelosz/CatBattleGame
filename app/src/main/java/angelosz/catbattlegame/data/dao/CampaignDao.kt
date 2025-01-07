package angelosz.catbattlegame.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import angelosz.catbattlegame.data.entities.CampaignChapter
import angelosz.catbattlegame.data.entities.CampaignChapterState
import angelosz.catbattlegame.data.entities.CampaignCompletionState
import angelosz.catbattlegame.data.entities.CampaignEntity
import angelosz.catbattlegame.data.entities.ChapterReward
import angelosz.catbattlegame.domain.enums.CampaignState

@Dao
interface CampaignDao {
    /* Campaign */
    @Insert
    suspend fun insertCampaign(campaign: CampaignEntity): Long
    @Insert
    suspend fun insertCampaigns(campaigns: List<CampaignEntity>)

    @Query("Select * from Campaign")
    suspend fun getAllCampaigns(): List<CampaignEntity>
    @Query("Select * from campaign where id = :id")
    suspend fun getCampaignById(id: Long): CampaignEntity
    @Query("Select state from campaign_state where campaignId = :campaignId")
    suspend fun getCampaignState(campaignId: Long): CampaignState
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateCampaignState(campaignCompletionState: CampaignCompletionState)

    /* Campaign Chapters */
    @Insert
    suspend fun insertCampaignChapter(campaignChapter: CampaignChapter): Long
    @Insert
    suspend fun insertCampaignChapters(campaignChapters: List<CampaignChapter>)

    @Update
    suspend fun updateCampaignChapter(campaignChapter: CampaignChapter)

    @Query("Select * from campaign_chapter where campaignId = :campaignId")
    suspend fun getCampaignChaptersByCampaignId(campaignId: Long): List<CampaignChapter>
    @Query("Select * from campaign_chapter where id = :id")
    suspend fun getCampaignChapterById(id: Long): CampaignChapter
    @Query("Select state from campaign_chapter_state where chapterId = :chapterId")
    suspend fun getChapterState(chapterId: Long): CampaignState
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateChapterState(campaignChapterState: CampaignChapterState)

    /* Chapter Rewards */
    @Insert
    suspend fun insertChapterReward(chapterReward: ChapterReward): Long
    @Insert
    suspend fun insertChapterRewards(chapterRewards: List<ChapterReward>)

    @Query("Select * from chapter_reward where chapterId = :chapterId")
    suspend fun getChapterRewards(chapterId: Long): List<ChapterReward>
}