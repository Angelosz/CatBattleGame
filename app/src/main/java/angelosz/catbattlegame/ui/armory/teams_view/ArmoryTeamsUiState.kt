package angelosz.catbattlegame.ui.armory.teams_view

import angelosz.catbattlegame.domain.enums.ScreenState
import angelosz.catbattlegame.ui.armory.data.ArmoryTeam

data class ArmoryTeamsUiState (
    val state: ScreenState = ScreenState.INITIALIZING,
    val teams: List<ArmoryTeam> = emptyList(),

    val selectedCatId: Int = 1,
    val selectedTeam: ArmoryTeam = ArmoryTeam(
        teamId = 1,
        teamName = "New Team",
        cats = listOf()
    ),
    val isTeamSelected: Boolean = false,

    val page: Int = 0,
    val pageLimit: Int = 12,
    val totalNumberOfCats: Int = 0,
)