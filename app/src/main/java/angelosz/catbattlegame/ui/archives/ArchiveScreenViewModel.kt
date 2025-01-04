package angelosz.catbattlegame.ui.archives

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import angelosz.catbattlegame.domain.enums.ScreenState
import angelosz.catbattlegame.ui.armory.enums.CollectionsView
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ArchiveScreenViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(ArchivesUiState())
    val uiState: StateFlow<ArchivesUiState> = _uiState

    fun selectCollection(collection: CollectionsView){
        _uiState.update {
            it.copy(
                selectedCollection = collection
            )
        }
    }

    fun setup(selectedView: CollectionsView) {
        _uiState.update { it.copy(screenState = ScreenState.LOADING) }

        viewModelScope.launch {
            try {
                _uiState.update {
                    it.copy(
                        selectedCollection = selectedView,
                        screenState = ScreenState.SUCCESS
                    )
                }
            } catch (e: Exception){
                _uiState.update { it.copy(screenState = ScreenState.FAILURE) }
            }
        }
    }
}