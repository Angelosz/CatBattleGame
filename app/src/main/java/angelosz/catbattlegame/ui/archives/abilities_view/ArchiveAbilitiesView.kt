package angelosz.catbattlegame.ui.archives.abilities_view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import angelosz.catbattlegame.ui.archives.composables.ArchiveAbilityDetailsCard
import angelosz.catbattlegame.ui.archives.composables.ArchiveCatGrid
import angelosz.catbattlegame.ui.archives.data.SimpleCatData
import angelosz.catbattlegame.ui.components.FailureCard
import angelosz.catbattlegame.ui.components.LoadingCard
import angelosz.catbattlegame.ui.components.PaginationButtons

@Composable
fun ArchiveAbilitiesView(
    viewModel: ArchiveAbilitiesViewModel,
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
                    HandleArchiveAbilityLandscapeView(uiState, viewModel)
                } else {
                    HandleArchiveAbilityPortraitView(uiState, viewModel)
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
fun HandleArchiveAbilityLandscapeView(
    uiState: ArchiveAbilitiesUiState,
    viewModel: ArchiveAbilitiesViewModel,
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
            ArchiveCatGrid(
                modifier = Modifier
                    .padding(8.dp)
                    .width(448.dp),
                cats = uiState.abilities.map { ability -> SimpleCatData(ability.id, ability.image) },
                onCatCardClicked = { viewModel.selectAbility(it.id)},
                pageLimit = uiState.pageLimit,
                imageSize = 96,
            )
            PaginationButtons(
                isNotFirstPage = uiState.page > 0,
                isNotLastPage = viewModel.isNotLastPage(),
                onPreviousButtonClicked = viewModel::goToPreviousPage,
                onNextButtonClicked = viewModel::goToNextPage
            )
        }
        ArchiveAbilityDetailsCard(
            modifier = Modifier.width(300.dp),
            ability = uiState.selectedAbility,
            imageSize = 256,
        )
    }
}

@Composable
fun HandleArchiveAbilityPortraitView(
    uiState: ArchiveAbilitiesUiState,
    viewModel: ArchiveAbilitiesViewModel,
) {
    if (uiState.isAbilitySelected) {
        ArchiveAbilityDetailsCard(
            modifier = Modifier.padding(16.dp),
            ability = uiState.selectedAbility,
        )
    } else {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ArchiveCatGrid(
                modifier = Modifier.padding(8.dp),
                cats = uiState.abilities.map { ability -> SimpleCatData(ability.id, ability.image) },
                onCatCardClicked = { viewModel.selectAbility(it.id) },
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

