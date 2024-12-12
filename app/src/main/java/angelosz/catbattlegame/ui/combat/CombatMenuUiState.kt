package angelosz.catbattlegame.ui.combat

import angelosz.catbattlegame.domain.enums.ScreenState

data class CombatMenuUiState (
    val screenState: ScreenState = ScreenState.LOADING,
)