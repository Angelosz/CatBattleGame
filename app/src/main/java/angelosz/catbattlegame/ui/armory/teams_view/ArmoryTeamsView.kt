package angelosz.catbattlegame.ui.armory.teams_view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import angelosz.catbattlegame.ui.armory.composables.ArmoryCatGrid
import angelosz.catbattlegame.ui.armory.composables.TeamDisplay
import angelosz.catbattlegame.ui.armory.data.ArmoryTeam
import angelosz.catbattlegame.ui.components.FailureCard
import angelosz.catbattlegame.ui.components.LoadingCard
import angelosz.catbattlegame.ui.components.PaginationButtons

@Composable
fun ArmoryTeamsView(
    viewModel: ArmoryTeamsViewModel = viewModel(factory = CatViewModelProvider.Factory),
    innerPadding: PaddingValues,
    onFailure: () -> Unit,
    pageLimit: Int = 12,
    isLandscapeView: Boolean = false
){
    val uiState by viewModel.uiState.collectAsState()

    when(uiState.state){
        ScreenState.SUCCESS -> {
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                if(isLandscapeView){
                    HandleArmoryTeamsLandscapeView(uiState, viewModel)
               }
                else{
                    HandleArmoryTeamsPortraitView(uiState, viewModel)
                }
            }
        }
        ScreenState.LOADING -> LoadingCard()
        ScreenState.FAILURE -> FailureCard(onFailure)
        ScreenState.INITIALIZING -> {
            LoadingCard()
            LaunchedEffect(viewModel) {
                viewModel.setup(pageLimit)
            }
        }
    }
}

@Composable
fun HandleArmoryTeamsPortraitView(uiState: ArmoryTeamsUiState, viewModel: ArmoryTeamsViewModel) {
    if(uiState.isTeamSelected){
        Column(
            modifier = Modifier.fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            Spacer(modifier = Modifier.weight(1f))
            ArmoryCatGrid(
                modifier = Modifier.padding(32.dp),
                cats = uiState.cats,
                onCatCardClicked = { viewModel.addCatToTeam(it) },
            )
            PaginationButtons(
                isNotFirstPage = uiState.page > 0,
                isNotLastPage = viewModel.isNotLastPage(),
                onNextButtonClicked = viewModel::goToNextPage,
                onPreviousButtonClicked = viewModel::goToPreviousPage,
            )
            TeamDisplay(
                modifier = Modifier.padding(8.dp),
                team = uiState.selectedTeam,
                onCatCardClicked = { viewModel.removeCatFromTeam(it) },
                isNameEditable = true,
                onNameEdited = viewModel::renameTeam
            )
            Spacer(modifier = Modifier.weight(1f))
        }
    }
    else{
        Column(
        ){
            LazyColumn{
                items(uiState.teams) { team ->
                    TeamDisplay(
                        modifier = Modifier.padding(8.dp),
                        team = team,
                        onTeamClicked = { id -> viewModel.selectTeam(id) },
                        hasButton = true,
                        buttonText = "Delete",
                        onButtonClick = viewModel::deleteTeam
                    )
                }
                item {
                    TeamDisplay(
                        modifier = Modifier.padding(8.dp),
                        team = ArmoryTeam(
                            teamId = 0,
                            teamName = "Create New Team",
                            cats = emptyList()
                        ),
                        onTeamClicked = { viewModel.createTeam() }
                    )
                }
            }
        }
    }
}

@Composable
fun HandleArmoryTeamsLandscapeView(uiState: ArmoryTeamsUiState, viewModel: ArmoryTeamsViewModel) {
    if(uiState.isTeamSelected){
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            ArmoryCatGrid(
                modifier = Modifier
                    .width(512.dp),
                cats = uiState.cats,
                pageLimit = 10,
                onCatCardClicked = { viewModel.addCatToTeam(it) },
            )
            PaginationButtons(
                isNotFirstPage = uiState.page < uiState.totalNumberOfCats / uiState.pageLimit,
                isNotLastPage = uiState.page > 0,
                onNextButtonClicked = {},
                onPreviousButtonClicked = {},
            )
            TeamDisplay(
                team = uiState.selectedTeam,
                onCatCardClicked = { viewModel.removeCatFromTeam(it) },
                isNameEditable = true,
                onNameEdited = viewModel::renameTeam,
                displayAsLongStrip = true
            )
        }
    }
    else{
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            LazyColumn{
                items(uiState.teams) { team ->
                    TeamDisplay(
                        modifier = Modifier.padding(8.dp),
                        team = team,
                        onTeamClicked = { id -> viewModel.selectTeam(id) },
                        hasButton = true,
                        buttonText = "Delete",
                        onButtonClick = viewModel::deleteTeam
                    )
                }
                item {
                    TeamDisplay(
                        modifier = Modifier.padding(8.dp),
                        team = ArmoryTeam(
                            teamId = 0,
                            teamName = "Create New Team",
                            cats = emptyList()
                        ),
                        onTeamClicked = { viewModel.createTeam() }
                    )
                }
            }
        }
    }

}



