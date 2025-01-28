package angelosz.catbattlegame.data.database.initialdata

import angelosz.catbattlegame.R
import angelosz.catbattlegame.data.entities.CampaignEntity

class CampaignsInitialData {
    val campaigns = listOf(
        CampaignEntity(
            id = 1,
            name = R.string.campaign_title_1,
            description = R.string.campaign_desc_1,
            image = R.drawable.house_campaign_icon_256,
            nextCampaign = 2
        ),
        CampaignEntity(
            id = 2,
            name = R.string.campaign_title_2,
            description = R.string.campaign_desc_2,
            image = R.drawable.garden_campaign_icon_256,
            nextCampaign = 3
        ),
        CampaignEntity(
            id = 3,
            name = R.string.campaign_title_3,
            description = R.string.campaign_desc_3,
            image = R.drawable.darkforest_campaign_icon_256,
            nextCampaign = 4
        ),
        CampaignEntity(
            id = 4,
            name = R.string.campaign_title_4,
            description = R.string.campaign_desc_4,
            image = R.drawable.piratecave_campaign_icon_256,
            nextCampaign = 5
        )
    )
}