package angelosz.catbattlegame.ui.archives

import androidx.activity.compose.BackHandler
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import angelosz.catbattlegame.CatViewModelProvider
import angelosz.catbattlegame.ui.armory.enums.CollectionsView
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update


@Composable
fun ArchivesScreen(
    windowSize: WindowWidthSizeClass,
    onBackPressed: () -> Unit,
    viewModel: ArchivesScreenViewModel = viewModel(factory = CatViewModelProvider.Factory),
    selectedView: CollectionsView = CollectionsView.CATS
){
    BackHandler(onBack = onBackPressed)
    val uiState by viewModel.uiState.collectAsState()
    val isLandscapeView = windowSize == WindowWidthSizeClass.Expanded


}

class ArchivesScreenViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(ArchivesUiState())
    val uiState: StateFlow<ArchivesUiState> = _uiState

    fun selectCollection(collection: CollectionsView){
        _uiState.update {
            it.copy(
                selectedCollection = collection
            )
        }
    }
}

data class ArchivesUiState (
    val selectedCollection: CollectionsView = CollectionsView.CATS,
)