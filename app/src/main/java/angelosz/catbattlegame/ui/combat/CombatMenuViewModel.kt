package angelosz.catbattlegame.ui.combat

import androidx.lifecycle.ViewModel
import angelosz.catbattlegame.domain.enums.ScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class CombatMenuViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(CombatMenuUiState())
    val uiState: StateFlow<CombatMenuUiState> = _uiState

    init {
        setupInitialData()
    }

    fun setupInitialData() {
        _uiState.update {
            it.copy(
                screenState = ScreenState.SUCCESS
            )
        }
    }
}