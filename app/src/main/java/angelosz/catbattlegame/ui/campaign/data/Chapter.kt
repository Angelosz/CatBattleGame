package angelosz.catbattlegame.ui.campaign.data

import androidx.annotation.DrawableRes
import angelosz.catbattlegame.R
import angelosz.catbattlegame.domain.enums.CampaignState

data class Chapter(
    val id: Long = 1,
    val campaignId: Long = 1,
    val order: Int = 1,
    val state: CampaignState = CampaignState.LOCKED,
    val experience: Int = 100,
    val name: String = "Chapter",
    val description: String = "Chapter description",
    @DrawableRes val image: Int = R.drawable.enemy_wool_ball_300,
    val unlocksChapter: Long = 0,
    val isLastCampaignChapter: Boolean = false
)