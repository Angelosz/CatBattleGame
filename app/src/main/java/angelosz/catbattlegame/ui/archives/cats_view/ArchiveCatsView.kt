package angelosz.catbattlegame.ui.archives.cats_view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import angelosz.catbattlegame.ui.archives.composables.ArchiveCatDetailsCard
import angelosz.catbattlegame.ui.archives.composables.ArchiveCatGrid
import angelosz.catbattlegame.ui.archives.composables.CatRarityFilter
import angelosz.catbattlegame.ui.components.FailureCard
import angelosz.catbattlegame.ui.components.LoadingCard
import angelosz.catbattlegame.ui.components.PaginationButtons

@Composable
fun ArchiveCatsView(
    viewModel: ArchiveCatsViewModel,
    innerPadding: PaddingValues,
    onFailure: () -> Unit,
    onAbilityClicked: (Int) -> Unit,
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
                    HandleArchiveCatsLandscapeView(uiState, viewModel, onAbilityClicked)
                } else {
                    HandleArchiveCatsPortraitView(uiState, viewModel, onAbilityClicked)
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
fun HandleArchiveCatsPortraitView(
    uiState: ArchiveCatsUiState,
    viewModel: ArchiveCatsViewModel,
    onAbilityClicked: (Int) -> Unit
) {
    if (uiState.isCatSelected) {
        ArchiveCatDetailsCard(
            modifier = Modifier.padding(16.dp),
            cat = uiState.selectedCat,
            playerCrystals = uiState.playerCrystals,
            onAbilityClicked = onAbilityClicked,
            onCatPurchased = { viewModel.catWasPurchased(it) }
        )
    } else {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CatRarityFilter(
                modifier = Modifier.align(Alignment.Start).padding(start = 32.dp),
                onRaritySelected = viewModel::filterCats,
                onRemoveSelection = viewModel::removeFilter
            )
            ArchiveCatGrid(
                modifier = Modifier.padding(8.dp),
                cats = uiState.cats,
                onCatCardClicked = { viewModel.selectCat(it.id) },
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
fun HandleArchiveCatsLandscapeView(
    uiState: ArchiveCatsUiState,
    viewModel: ArchiveCatsViewModel,
    onAbilityClicked: (Int) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.width(448.dp),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Center
        ) {
            ArchiveCatGrid(
                modifier = Modifier
                    .padding(8.dp)
                    .width(448.dp),
                cats = uiState.cats,
                onCatCardClicked = { viewModel.selectCat(it.id)},
                pageLimit = uiState.pageLimit,
                imageSize = 96,
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ){
                CatRarityFilter(
                    onRaritySelected = viewModel::filterCats,
                    onRemoveSelection = viewModel::removeFilter
                )
                PaginationButtons(
                    isNotFirstPage = uiState.page > 0,
                    isNotLastPage = viewModel.isNotLastPage(),
                    onPreviousButtonClicked = viewModel::goToPreviousPage,
                    onNextButtonClicked = viewModel::goToNextPage
                )
            }
        }
        ArchiveCatDetailsCard(
            modifier = Modifier.width(300.dp),
            cat = uiState.selectedCat,
            imageSize = 192,
            playerCrystals = uiState.playerCrystals,
            onAbilityClicked = onAbilityClicked,
            onCatPurchased = { viewModel.catWasPurchased(it) }
        )
    }
}