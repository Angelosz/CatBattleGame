package angelosz.catbattlegame.ui.campaign

import angelosz.catbattlegame.domain.enums.ScreenState
import angelosz.catbattlegame.data.entities.Campaign
import angelosz.catbattlegame.data.entities.CampaignChapter

enum class CampaignSelectionStage {
    SELECTING_CAMPAIGN,
    SELECTING_CHAPTER,
    CHAPTER_SELECTED
}

data class CampaignScreenUiState (
    val screenState: ScreenState = ScreenState.LOADING,
    val campaigns: List<Campaign> = listOf(),
    val selectedCampaign: Campaign = Campaign(0),
    val campaignChapters: List<CampaignChapter> = listOf(),
    val selectedCampaignChapter: CampaignChapter = CampaignChapter(),
    val selectedCampaignChapterEnemyCats: List<SimplifiedEnemyCatData> = listOf(),
    val stage: CampaignSelectionStage = CampaignSelectionStage.SELECTING_CAMPAIGN
)