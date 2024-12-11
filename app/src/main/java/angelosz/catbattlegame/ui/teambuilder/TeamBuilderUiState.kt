package angelosz.catbattlegame.ui.teambuilder

import angelosz.catbattlegame.domain.enums.ScreenState
import angelosz.catbattlegame.domain.models.OwnedCatDetailsData

data class TeamBuilderUiState (
    val teams: List<TeamData> = listOf(),
    val selectedTeam: TeamData = TeamData(
        teamId = 1,
        teamName = "Default Team",
        cats = listOf()
    ),
    val pageCatsData: List<BasicCatData> = listOf(),
    val catListPage: Int = 1,
    val selectedCat: OwnedCatDetailsData = OwnedCatDetailsData(),
    val ownedCatCount: Int = 0,
    val teamIsSelected: Boolean = false,
    val screenState: ScreenState = ScreenState.LOADING
)
