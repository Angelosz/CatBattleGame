package angelosz.catbattlegame.ui.archives

import angelosz.catbattlegame.domain.enums.ScreenState
import angelosz.catbattlegame.ui.armory.enums.CollectionsView

data class ArchivesUiState (
    val screenState: ScreenState = ScreenState.INITIALIZING,
    val selectedCollection: CollectionsView = CollectionsView.CATS,
)