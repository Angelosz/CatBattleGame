package angelosz.catbattlegame.domain.models.entities

import androidx.annotation.DrawableRes
import androidx.room.Entity
import androidx.room.PrimaryKey
import angelosz.catbattlegame.R
import angelosz.catbattlegame.domain.enums.CampaignState

@Entity(tableName = "campaign")
data class Campaign (
    @PrimaryKey
    val id: Long,
    val state: CampaignState = CampaignState.LOCKED,
    val name: String = "Campaign",
    val description: String = "Campaign description",
    @DrawableRes val image: Int = R.drawable.house_campaign_icon_256,
    val nextCampaign: Long = 0,
)
