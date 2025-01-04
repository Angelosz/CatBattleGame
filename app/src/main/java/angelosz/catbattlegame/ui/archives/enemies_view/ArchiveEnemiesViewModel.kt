package angelosz.catbattlegame.ui.archives.enemies_view

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ArchiveEnemiesViewModel: ViewModel() {
    fun setup() {

    }

    private val _uiState = MutableStateFlow(ArchiveEnemiesUiState())
    val uiState: StateFlow<ArchiveEnemiesUiState> = _uiState

}