package angelosz.catbattlegame.ui.campaign.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import angelosz.catbattlegame.R
import angelosz.catbattlegame.domain.enums.CampaignState

data class Chapter(
    val id: Long = 1,
    val campaignId: Long = 1,
    val order: Int = 1,
    val state: CampaignState = CampaignState.LOCKED,
    val experience: Int = 100,
    @StringRes val name: Int = R.string.chapter_title_1,
    @StringRes val description: Int = R.string.chapter_desc_1,
    @DrawableRes val image: Int = R.drawable.enemy_wool_ball_300,
    val unlocksChapter: Long = 0,
    val isLastCampaignChapter: Boolean = false
)