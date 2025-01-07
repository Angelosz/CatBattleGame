package angelosz.catbattlegame.ui.campaign

import angelosz.catbattlegame.domain.enums.ScreenState
import angelosz.catbattlegame.ui.campaign.data.Campaign
import angelosz.catbattlegame.ui.campaign.data.Chapter

enum class CampaignSelectionStage {
    SELECTING_CAMPAIGN,
    SELECTING_CHAPTER,
    CHAPTER_SELECTED
}

data class CampaignScreenUiState (
    val screenState: ScreenState = ScreenState.INITIALIZING,
    val campaigns: List<Campaign> = listOf(),
    val selectedCampaign: Campaign = Campaign(0),
    val campaignChapters: List<Chapter> = listOf(),
    val selectedCampaignChapter: Chapter = Chapter(),
    val selectedCampaignChapterEnemyCats: List<SimplifiedEnemyCatData> = listOf(),
    val stage: CampaignSelectionStage = CampaignSelectionStage.SELECTING_CAMPAIGN
)