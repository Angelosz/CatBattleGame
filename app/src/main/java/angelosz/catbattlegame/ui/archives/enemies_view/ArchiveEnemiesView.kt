package angelosz.catbattlegame.ui.archives.enemies_view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import angelosz.catbattlegame.domain.enums.ScreenState
import angelosz.catbattlegame.ui.archives.composables.ArchiveEnemyDetailsCard
import angelosz.catbattlegame.ui.archives.composables.ArchiveEnemyGrid
import angelosz.catbattlegame.ui.components.FailureCard
import angelosz.catbattlegame.ui.components.LoadingCard
import angelosz.catbattlegame.ui.components.PaginationButtons


@Composable
fun ArchiveEnemiesView(
    viewModel: ArchiveEnemiesViewModel,
    innerPadding: PaddingValues,
    onFailure: () -> Unit,
    isLandscapeView: Boolean,
) {
    val uiState by viewModel.uiState.collectAsState()

    when(uiState.screenState){
        ScreenState.SUCCESS -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ){
                if(isLandscapeView){
                    HandleArchiveEnemiesLandscapeView(uiState, viewModel)
                } else {
                    HandleArchiveEnemiesPortraitView(uiState, viewModel)
                }
            }
        }
        ScreenState.LOADING -> LoadingCard()
        ScreenState.FAILURE -> FailureCard(onFailure)
        ScreenState.INITIALIZING -> {
            LoadingCard()
            LaunchedEffect(viewModel) {
                viewModel.setup(pageLimit = 12)
            }
        }
    }
}

@Composable
fun HandleArchiveEnemiesPortraitView(
    uiState: ArchiveEnemiesUiState,
    viewModel: ArchiveEnemiesViewModel,
) {
    if (uiState.isEnemySelected) {
        ArchiveEnemyDetailsCard(
            modifier = Modifier.padding(16.dp),
            enemy = uiState.selectedEnemy,
        )
    } else {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ArchiveEnemyGrid(
                modifier = Modifier.padding(8.dp),
                enemies = uiState.enemies,
                onCatCardClicked = { viewModel.selectEnemy(it.id) },
                pageLimit = uiState.pageLimit,
                imageSize = 112,
            )
            PaginationButtons(
                isNotFirstPage = uiState.page > 0,
                isNotLastPage = viewModel.isNotLastPage(),
                onPreviousButtonClicked = viewModel::goToPreviousPage,
                onNextButtonClicked = viewModel::goToNextPage
            )
        }
    }
}


@Composable
fun HandleArchiveEnemiesLandscapeView(
    uiState: ArchiveEnemiesUiState,
    viewModel: ArchiveEnemiesViewModel,
) {
    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(1f),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Center
        ) {
            ArchiveEnemyGrid(
                modifier = Modifier
                    .padding(8.dp)
                    .width(448.dp),
                enemies = uiState.enemies,
                onCatCardClicked = { viewModel.selectEnemy(it.id) },
                pageLimit = uiState.pageLimit,
                imageSize = 112,
            )
            PaginationButtons(
                isNotFirstPage = uiState.page > 0,
                isNotLastPage = viewModel.isNotLastPage(),
                onPreviousButtonClicked = viewModel::goToPreviousPage,
                onNextButtonClicked = viewModel::goToNextPage
            )
        }
        if (uiState.isEnemySelected){
            ArchiveEnemyDetailsCard(
                modifier = Modifier.padding(16.dp).width(332.dp),
                enemy = uiState.selectedEnemy,
            )
        } else {
            Spacer(modifier = Modifier.width(332.dp).padding(16.dp))
        }
    }
}