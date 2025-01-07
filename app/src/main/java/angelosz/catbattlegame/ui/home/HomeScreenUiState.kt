package angelosz.catbattlegame.ui.home

import angelosz.catbattlegame.domain.enums.ScreenState

data class HomeScreenUiState (
    val screenState: ScreenState = ScreenState.INITIALIZING,
    val gold: Int = 0,
    val crystals: Int = 0
)