package angelosz.catbattlegame.ui.campaign.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import angelosz.catbattlegame.R
import angelosz.catbattlegame.domain.enums.CampaignState

data class Campaign (
    val id: Long,
    val state: CampaignState = CampaignState.LOCKED,
    @StringRes val name: Int = R.string.campaign_title_1,
    @StringRes val description: Int = R.string.campaign_desc_1,
    @DrawableRes
    val image: Int = R.drawable.house_campaign_icon_256,
    val nextCampaign: Long = 0,
)