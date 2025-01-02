package angelosz.catbattlegame.ui.armory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import angelosz.catbattlegame.domain.enums.ScreenState
import angelosz.catbattlegame.ui.armory.enums.CollectionsView
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ArmoryScreenViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(ArmoryUiState())
    val uiState: StateFlow<ArmoryUiState> = _uiState

    fun selectCollection(collection: CollectionsView){
        _uiState.update {
            it.copy(
                selectedCollection = collection,
                screenState = ScreenState.SUCCESS
            )
        }
    }

    fun setup(selectCollection: CollectionsView) {
        _uiState.update {
            it.copy(
                screenState = ScreenState.LOADING
            )
        }
        viewModelScope.launch {
            try {
                delay(100)
                selectCollection(selectCollection)
            } catch (e: Exception){
                _uiState.update { it.copy(screenState = ScreenState.FAILURE) }
            }
        }
    }
}