package angelosz.catbattlegame.ui.armory.battlechest_view

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ArmoryBattleChestViewModel: ViewModel() {
    fun setup() {

    }

    private val _uiState = MutableStateFlow(ArmoryBattleChestUiState())
    val uiState: StateFlow<ArmoryBattleChestUiState> = _uiState
}