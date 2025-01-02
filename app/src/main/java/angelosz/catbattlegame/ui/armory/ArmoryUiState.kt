package angelosz.catbattlegame.ui.armory

import angelosz.catbattlegame.domain.enums.ScreenState
import angelosz.catbattlegame.ui.armory.enums.CollectionsView

data class ArmoryUiState (
    val selectedCollection: CollectionsView = CollectionsView.CATS,
    val screenState: ScreenState = ScreenState.INITIALIZING,
)