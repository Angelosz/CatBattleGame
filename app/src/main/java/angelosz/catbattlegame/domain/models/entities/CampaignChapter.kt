package angelosz.catbattlegame.domain.models.entities

import androidx.annotation.DrawableRes
import androidx.room.Entity
import androidx.room.PrimaryKey
import angelosz.catbattlegame.R
import angelosz.catbattlegame.domain.enums.CampaignState

@Entity(tableName = "campaign_chapter")
data class CampaignChapter(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val campaignId: Long = 1,
    val order: Int = 1,
    val state: CampaignState = CampaignState.LOCKED,
    val name: String = "Chapter",
    val description: String = "Chapter description",
    @DrawableRes val image: Int = R.drawable.enemy_wool_ball_300
)