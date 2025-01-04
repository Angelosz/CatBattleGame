package angelosz.catbattlegame.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import angelosz.catbattlegame.domain.enums.CampaignState

@Entity(tableName = "campaign_state")
data class CampaignCompletionState (
    @PrimaryKey
    val campaignId: Long,
    val state: CampaignState = CampaignState.LOCKED,
)