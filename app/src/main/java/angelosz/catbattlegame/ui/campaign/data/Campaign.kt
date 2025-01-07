package angelosz.catbattlegame.ui.campaign.data

import androidx.annotation.DrawableRes
import angelosz.catbattlegame.R
import angelosz.catbattlegame.domain.enums.CampaignState

data class Campaign (
    val id: Long,
    val state: CampaignState = CampaignState.LOCKED,
    val name: String = "Campaign",
    val description: String = "Campaign description",
    @DrawableRes
    val image: Int = R.drawable.house_campaign_icon_256,
    val nextCampaign: Long = 0,
)