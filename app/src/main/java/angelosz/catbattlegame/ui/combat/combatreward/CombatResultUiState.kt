package angelosz.catbattlegame.ui.combat.combatreward

import angelosz.catbattlegame.data.entities.CampaignChapter
import angelosz.catbattlegame.data.entities.ChapterReward
import angelosz.catbattlegame.domain.enums.CombatResult
import angelosz.catbattlegame.domain.enums.RewardType
import angelosz.catbattlegame.domain.enums.ScreenState
import angelosz.catbattlegame.ui.combat.BasicCatData

data class CombatResultUiState(
    val chapter: CampaignChapter? = null,
    val team: List<BasicCatData> = emptyList(),
    val chapterReward: List<ChapterReward> = listOf(
        ChapterReward(
            chapterId = 0,
            rewardType = RewardType.GOLD,
            amount = 10
        )
    ),
    val experienceGained: Int = 0,
    val combatResult: CombatResult = CombatResult.PLAYER_LOST,
    val screenState: ScreenState = ScreenState.INITIALIZING
)
