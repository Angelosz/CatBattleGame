package angelosz.catbattlegame.ui.home

import angelosz.catbattlegame.data.datastore.PlayerSettings
import angelosz.catbattlegame.domain.enums.ScreenState
import angelosz.catbattlegame.ui.home.notifications.HomeNotification

data class HomeScreenUiState (
    val screenState: ScreenState = ScreenState.INITIALIZING,
    val notifications: List<HomeNotification> = emptyList(),
    val shopIsOpen: Boolean = false,
    val settingsIsOpen: Boolean = false,
    val playerSettings: PlayerSettings = PlayerSettings(),
    val gold: Int = 0,
    val crystals: Int = 0,
)