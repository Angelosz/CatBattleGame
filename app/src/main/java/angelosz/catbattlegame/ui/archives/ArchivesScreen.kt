package angelosz.catbattlegame.ui.archives

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import angelosz.catbattlegame.CatViewModelProvider
import angelosz.catbattlegame.domain.enums.ScreenState
import angelosz.catbattlegame.ui.armory.composables.CollectionsBottomBar
import angelosz.catbattlegame.ui.armory.composables.CollectionsNavigationRail
import angelosz.catbattlegame.ui.armory.data.archivesNavigationItems
import angelosz.catbattlegame.ui.armory.enums.CollectionsView
import angelosz.catbattlegame.ui.components.FailureCard
import angelosz.catbattlegame.ui.components.LoadingCard
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


@Composable
fun ArchivesScreen(
    windowSize: WindowWidthSizeClass,
    returnToMenu: () -> Unit,
    viewModel: ArchivesScreenViewModel = viewModel(factory = CatViewModelProvider.Factory),
    selectedView: CollectionsView = CollectionsView.CATS
){
    BackHandler(onBack = returnToMenu)
    val uiState by viewModel.uiState.collectAsState()
    val isLandscapeView = windowSize == WindowWidthSizeClass.Expanded

    when(uiState.screenState){
        ScreenState.SUCCESS -> {
            Scaffold(
                bottomBar = {
                    if(!isLandscapeView)
                        CollectionsBottomBar(
                            selectedView = uiState.selectedCollection,
                            onTabPressed = viewModel::selectCollection,
                            onBackPressed = returnToMenu,
                            items = archivesNavigationItems
                        )
                },
                content = { innerPadding ->
                    if(isLandscapeView)
                        CollectionsNavigationRail(
                            selectedView = uiState.selectedCollection,
                            onTabPressed = viewModel::selectCollection,
                            onBackPressed = returnToMenu,
                            items = archivesNavigationItems
                        )
                    when(uiState.selectedCollection){
                        CollectionsView.CATS -> {
                            Text(
                                modifier = Modifier.padding(innerPadding),
                                text = "CATS"
                            )
                        }
                        CollectionsView.ABILITIES -> {
                            Text(
                                modifier = Modifier.padding(innerPadding),
                                text = "ABILITIES"
                            )
                        }
                        CollectionsView.ENEMIES -> {
                            Text(
                                modifier = Modifier.padding(innerPadding),
                                text = "ENEMIES"
                            )
                        }
                        else -> FailureCard(returnToMenu)
                    }
                }
            )

        }
        ScreenState.LOADING -> LoadingCard()
        ScreenState.FAILURE -> FailureCard(returnToMenu)
        ScreenState.INITIALIZING -> {
            LoadingCard()
            viewModel.setup(selectedView)
        }
    }

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

data class ArchivesUiState (
    val screenState: ScreenState = ScreenState.INITIALIZING,
    val selectedCollection: CollectionsView = CollectionsView.CATS,
)