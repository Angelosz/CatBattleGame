package angelosz.catbattlegame.ui.armory

import angelosz.catbattlegame.domain.enums.ScreenState

data class ArmoryUiState (
    val selectedCollection: ArmoryView = ArmoryView.CATS,
    val screenState: ScreenState = ScreenState.INITIALIZING,
)