package angelosz.catbattlegame.data.database.initialdata

import angelosz.catbattlegame.R
import angelosz.catbattlegame.data.entities.CampaignEntity

class CampaignsInitialData {
    val campaigns = listOf(
        CampaignEntity(
            id = 1,
            name = "Dangers in the house",
            description = "\"Defend your owner's house against dangerous enemies(and toys)!\"",
            image = R.drawable.house_campaign_icon_256,
            nextCampaign = 2
        ),
        CampaignEntity(
            id = 2,
            name = "Dangers in the garden",
            description = "\"Defend your owner's garden against the thieves!\"",
            image = R.drawable.garden_campaign_icon_256,
            nextCampaign = 3
        ),
        CampaignEntity(
            id = 3,
            name = "Dangers in the dark forest",
            description = "\"Pursue the thieves that stole your owner's treasure!\"",
            image = R.drawable.darkforest_campaign_icon_256,
            nextCampaign = 4
        ),
        CampaignEntity(
            id = 4,
            name = "Dangers in the pirate cave",
            description = "\"Assault the pirate cave and retrieve your owner's treasure!\"",
            image = R.drawable.piratecave_campaign_icon_256,
            nextCampaign = 5
        )
    )
}