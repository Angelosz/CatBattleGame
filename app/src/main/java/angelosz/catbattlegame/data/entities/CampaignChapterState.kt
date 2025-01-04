package angelosz.catbattlegame.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import angelosz.catbattlegame.domain.enums.CampaignState

@Entity(tableName = "campaign_chapter_state")
data class CampaignChapterState (
    @PrimaryKey
    val chapterId: Long,
    val state: CampaignState = CampaignState.LOCKED,
)