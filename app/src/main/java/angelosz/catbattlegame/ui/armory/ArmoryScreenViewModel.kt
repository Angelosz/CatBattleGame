package angelosz.catbattlegame.ui.armory

import androidx.lifecycle.ViewModel
import angelosz.catbattlegame.domain.enums.ScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class ArmoryScreenViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(ArmoryUiState())
    val uiState: StateFlow<ArmoryUiState> = _uiState

    fun selectCollection(collection: ArmoryView){
        _uiState.update {
            it.copy(
                selectedCollection = collection,
                screenState = ScreenState.SUCCESS
            )
        }
    }

    fun setup(selectCollection: ArmoryView) {
        _uiState.update {
            it.copy(
                screenState = ScreenState.LOADING
            )
        }
        selectCollection(selectCollection)
    }
}