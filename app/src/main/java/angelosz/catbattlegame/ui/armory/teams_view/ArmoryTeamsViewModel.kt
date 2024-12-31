package angelosz.catbattlegame.ui.armory.teams_view

import androidx.lifecycle.ViewModel
import angelosz.catbattlegame.domain.enums.ScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class ArmoryTeamsViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(ArmoryTeamsUiState())
    val uiState: StateFlow<ArmoryTeamsUiState> = _uiState

    fun setup(pageLimit: Int) {
        _uiState.update { it.copy( state = ScreenState.LOADING, pageLimit = pageLimit ) }
    }
}