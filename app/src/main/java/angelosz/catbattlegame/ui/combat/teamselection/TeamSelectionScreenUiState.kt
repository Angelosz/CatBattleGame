package angelosz.catbattlegame.ui.combat.teamselection

import angelosz.catbattlegame.domain.enums.ScreenState
import angelosz.catbattlegame.ui.combat.TeamData

data class TeamSelectionScreenUiState(
    val chapterId: Long = 1,
    val teamId: Long = 1,
    val teams: List<TeamData> = listOf(),
    val screenState: ScreenState = ScreenState.LOADING,
)