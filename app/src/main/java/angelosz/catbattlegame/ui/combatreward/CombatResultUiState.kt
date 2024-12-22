package angelosz.catbattlegame.ui.combatreward

import angelosz.catbattlegame.domain.enums.CombatResult
import angelosz.catbattlegame.domain.enums.RewardType
import angelosz.catbattlegame.domain.enums.ScreenState
import angelosz.catbattlegame.domain.models.entities.ChapterReward

data class CombatResultUiState(
    val chapterReward: List<ChapterReward> = listOf(
        ChapterReward(
            chapterId = 0,
            rewardType = RewardType.GOLD,
            amount = 10
        )
    ),
    val rewardCollected: Boolean = false,
    val combatResult: CombatResult = CombatResult.PLAYER_LOST,
    val screenState: ScreenState = ScreenState.INITIALIZING
)
