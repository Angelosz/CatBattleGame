package angelosz.catbattlegame.data.entities

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.room.Entity
import androidx.room.PrimaryKey
import angelosz.catbattlegame.R

@Entity(tableName = "campaign_chapter")
data class CampaignChapter(
    @PrimaryKey
    val id: Long = 1,
    val campaignId: Long = 1,
    val order: Int = 1,
    val experience: Int = 100,
    @StringRes val name: Int = R.string.chapter_title_1,
    @StringRes val description: Int = R.string.chapter_desc_1,
    @DrawableRes val image: Int = R.drawable.enemy_wool_ball_300,
    val unlocksChapter: Long = 0,
    val isLastCampaignChapter: Boolean = false
)