package angelosz.catbattlegame.ui.archives.enemies_view

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import angelosz.catbattlegame.domain.enums.ScreenState
import angelosz.catbattlegame.ui.components.FailureCard
import angelosz.catbattlegame.ui.components.LoadingCard


@Composable
fun ArchiveEnemiesView(
    viewModel: ArchiveEnemiesViewModel,
    innerPadding: PaddingValues,
    onFailure: () -> Unit,
    isLandscapeView: Boolean,
) {
    val uiState by viewModel.uiState.collectAsState()

    when(uiState.screenState){
        ScreenState.SUCCESS -> TODO()
        ScreenState.LOADING -> LoadingCard()
        ScreenState.FAILURE -> FailureCard(onFailure)
        ScreenState.INITIALIZING -> {
            LoadingCard()
            LaunchedEffect(viewModel) {
                viewModel.setup()
            }
        }
    }
}

