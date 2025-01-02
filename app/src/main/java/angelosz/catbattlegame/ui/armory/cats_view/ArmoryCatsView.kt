package angelosz.catbattlegame.ui.armory.cats_view

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
import androidx.lifecycle.viewmodel.compose.viewModel
import angelosz.catbattlegame.CatViewModelProvider
import angelosz.catbattlegame.domain.enums.ScreenState
import angelosz.catbattlegame.ui.armory.composables.ArmoryCatDetailsCard
import angelosz.catbattlegame.ui.armory.composables.ArmoryCatGrid
import angelosz.catbattlegame.ui.components.FailureCard
import angelosz.catbattlegame.ui.components.LoadingCard
import angelosz.catbattlegame.ui.components.PaginationButtons

@Composable
fun ArmoryCatsView(
    viewModel: ArmoryCatsViewModel = viewModel(factory = CatViewModelProvider.Factory),
    innerPadding: PaddingValues,
    onFailure: () -> Unit,
    pageLimit: Int = 12,
    isLandscapeView: Boolean = false
) {
    val uiState by viewModel.uiState.collectAsState()

    when(uiState.state){
        ScreenState.SUCCESS -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ){
                if(isLandscapeView){
                    HandleArmoryCatsLandscapeView(uiState, viewModel)
                } else {
                    HandleArmoryCatsPortraitView(uiState, viewModel)
                }
            }
        }
        ScreenState.LOADING -> {
            LoadingCard()
        }
        ScreenState.FAILURE -> {
            FailureCard(onFailure)
        }
        ScreenState.INITIALIZING -> {
            LoadingCard()
            LaunchedEffect(viewModel){
                viewModel.setup(pageLimit)
            }
        }
    }
}

@Composable
fun HandleArmoryCatsLandscapeView(
    uiState: ArmoryCatsUiState,
    viewModel: ArmoryCatsViewModel
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
                ArmoryCatGrid(
                    modifier = Modifier
                        .padding(8.dp)
                        .width(448.dp),
                    cats = uiState.cats,
                    onCatCardClicked = { viewModel.selectCat(it.id)},
                    pageLimit = uiState.pageLimit,
                    imageSize = 96,
                    showExperience = true
                )
                PaginationButtons(
                    isNotFirstPage = uiState.page > 0,
                    isNotLastPage = viewModel.isNotLastPage(),
                    onPreviousButtonClicked = viewModel::goToPreviousPage,
                    onNextButtonClicked = viewModel::goToNextPage
                )
            }
            ArmoryCatDetailsCard(
                modifier = Modifier.width(300.dp),
                cat = uiState.selectedCat,
                onCloseClicked = viewModel::exitDetailView,
                imageSize = 256,
            )
        }
}

@Composable
private fun HandleArmoryCatsPortraitView(
    uiState: ArmoryCatsUiState,
    viewModel: ArmoryCatsViewModel,
) {
    if (uiState.isDetailView) {
        ArmoryCatDetailsCard(
            modifier = Modifier.padding(16.dp),
            cat = uiState.selectedCat,
            onCloseClicked = viewModel::exitDetailView,
        )
    } else {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ArmoryCatGrid(
                modifier = Modifier.padding(8.dp),
                cats = uiState.cats,
                onCatCardClicked = { viewModel.selectCat(it.id) },
                pageLimit = uiState.pageLimit,
                imageSize = 112,
                showExperience = true
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
