package angelosz.catbattlegame.ui.teambuilder

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import angelosz.catbattlegame.CatViewModelProvider
import angelosz.catbattlegame.R
import angelosz.catbattlegame.domain.enums.ScreenState
import angelosz.catbattlegame.domain.models.OwnedCatDetailsData
import angelosz.catbattlegame.ui.components.BackButton
import angelosz.catbattlegame.ui.components.BackgroundImage
import angelosz.catbattlegame.ui.collections.CatImageCardGrid
import angelosz.catbattlegame.ui.components.FailureCard
import angelosz.catbattlegame.ui.components.LoadingCard
import angelosz.catbattlegame.ui.components.PaginationButtons
import angelosz.catbattlegame.ui.components.CatCard

@Composable
fun TeamBuilderScreen(
    windowSize: WindowWidthSizeClass,
    onBackPressed: () -> Unit
) {
    BackHandler( onBack = onBackPressed )

    val viewModel:TeamBuilderViewModel = viewModel(factory = CatViewModelProvider.Factory)
    val uiState by viewModel.uiState.collectAsState()

    val isPortraitView = windowSize != WindowWidthSizeClass.Expanded

    Box(
        contentAlignment = Alignment.TopCenter
    ) {
        BackgroundImage(R.drawable.encyclopedia_landscape_blurry)
        when(uiState.screenState){
            ScreenState.SUCCESS -> {
                if(isPortraitView){
                    HandlePortraitView(uiState, viewModel)
                } else {
                    HandleLandscapeView(uiState, viewModel)
                }
            }
            ScreenState.LOADING -> {
                LoadingCard()
            }
            ScreenState.FAILURE -> {
                FailureCard(
                    onBackPressed = onBackPressed,
                    onReloadPressed = { viewModel.setupInitialData() }
                )
            }
            ScreenState.INITIALIZING -> {}
        }

        if (!uiState.teamIsSelected) {
            BackButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(horizontal = 32.dp, vertical = 56.dp),
                onBackPressed = onBackPressed
            )
        }
    }
}

@Composable
fun HandleLandscapeView(uiState: TeamBuilderUiState, viewModel: TeamBuilderViewModel) {
    Row(
        modifier = Modifier
            .systemBarsPadding()
            .fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (!uiState.teamIsSelected) {
            TeamsList(
                teams = uiState.teams,
                selectTeam = { teamData -> viewModel.selectTeam(teamData.teamId) },
                deleteTeam = { teamData -> viewModel.deleteTeam(teamData.teamId) },
                createTeam = { _ -> viewModel.createNewTeam() },
                columns = 2,
                canDeleteTeam = true,
                canCreateTeam = true
            )
        } else {
            BackHandler { viewModel.goBackToTeamList() }

            TeamCatsGridPage(
                modifier = Modifier
                    .padding(12.dp)
                    .width(332.dp),
                uiState = uiState,
                viewModel = viewModel,
                imageSize = 92
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                val selectedCat = uiState.selectedCat
                SelectedCatInfoCard(
                    selectedCat = selectedCat,
                    onAddClicked = { viewModel.addSelectedCatToTeam() }
                )
                Spacer(modifier = Modifier.weight(1f))

                TeamPanel(
                    teamData = uiState.selectedTeam,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                    imageSize = 80,
                    spacedBy = 8,
                    onTeamCardClicked = { },
                    onDeleteTeamClicked = { teamData -> viewModel.deleteTeam(teamData.teamId) },
                    onCatClicked = { catId -> viewModel.removeCatFromSelectedTeam(catId) },
                    isNameEditable = true
                )
            }
        }
    }
}

@Composable
private fun HandlePortraitView(
    uiState: TeamBuilderUiState,
    viewModel: TeamBuilderViewModel,
) {
    Column(
        modifier = Modifier
            .systemBarsPadding()
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (!uiState.teamIsSelected) {
            TeamsList(
                teams = uiState.teams,
                selectTeam = { teamData -> viewModel.selectTeam(teamData.teamId) },
                deleteTeam = { teamData -> viewModel.deleteTeam(teamData.teamId) },
                createTeam = { _ -> viewModel.createNewTeam() },
                canDeleteTeam = true,
                canCreateTeam = true
            )
        } else {
            BackHandler { viewModel.goBackToTeamList() }

            TeamCatsGridPage(
                modifier = Modifier
                    .padding(16.dp)
                    .height(420.dp)
                    .fillMaxWidth(),
                uiState = uiState,
                viewModel = viewModel,
            )

            val selectedCat = uiState.selectedCat
            SelectedCatInfoCard(
                selectedCat = selectedCat,
                onAddClicked = { viewModel.addSelectedCatToTeam() }
            )

            Spacer(modifier = Modifier.weight(1f))

            TeamPanel(
                teamData = uiState.selectedTeam,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                imageSize = 80,
                spacedBy = 8,
                onTeamCardClicked = { },
                onDeleteTeamClicked = { teamData -> viewModel.deleteTeam(teamData.teamId) },
                onCatClicked = { catId -> viewModel.removeCatFromSelectedTeam(catId) },
                isNameEditable = true,
                onNameChanged = viewModel::updateTeamName
            )
        }
    }
}

@Composable
fun SelectedCatInfoCard(
    selectedCat: OwnedCatDetailsData,
    onAddClicked: () -> Unit,
) {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .height(192.dp),
        shape = RectangleShape,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                CatCard(
                    id = selectedCat.cat.id,
                    image = selectedCat.cat.image,
                    onCardClicked = {},
                    imageSize = 128,
                    showBorder = true
                )
                Button(
                    onClick = onAddClicked,
                ) {
                    Text(
                        text = "Add",
                        style = MaterialTheme.typography.labelMedium,
                    )
                }
            }
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 6.dp)
            ) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    shape = RectangleShape,
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(6.dp)
                    ) {
                        Text(
                            text = selectedCat.cat.name,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Text(
                            text = "Health: ${selectedCat.cat.baseHealth + selectedCat.ownedCatData.healthModifier}"
                        )
                        Text(
                            text = "Attack: ${selectedCat.cat.baseAttack + selectedCat.ownedCatData.attackModifier}"
                        )
                        Text(
                            text = "Defense: ${selectedCat.cat.baseDefense + selectedCat.ownedCatData.defenseModifier}"
                        )
                        Text(
                            text = "Attack Speed: ${selectedCat.cat.attackSpeed + selectedCat.ownedCatData.attackSpeedModifier}"
                        )
                        Text(
                            text = "Armor Type: ${selectedCat.cat.armorType}"
                        )
                    }
                }
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    shape = RectangleShape,
                    modifier = Modifier
                        .padding(horizontal = 6.dp)
                ) {
                    // Abilities?
                }
            }
        }
    }
}

@Composable
fun TeamCatsGridPage(
    modifier: Modifier = Modifier,
    uiState: TeamBuilderUiState,
    viewModel: TeamBuilderViewModel,
    imageSize: Int = 112,
) {
    Card(
        modifier = modifier,
        shape = RectangleShape,
    ){
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CatImageCardGrid(
                catsData = uiState.pageCatsData,
                pageLimit = viewModel.paginationLimit,
                imageSize = imageSize,
                onCardSelected = { id -> viewModel.selectCat(id) })
            PaginationButtons(
                isNotFirstPage = uiState.catListPage > 1,
                isNotLastPage = uiState.catListPage * viewModel.paginationLimit < uiState.ownedCatCount,
                onPreviousButtonClicked = { viewModel.showPreviousCatsPage() },
                onNextButtonClicked = { viewModel.showNextCatsPage() }
            )
        }
    }
}

@Composable
fun TeamsList(
    teams: List<TeamData>,
    selectTeam: (TeamData) -> Unit = {},
    deleteTeam: (TeamData) -> Unit = {},
    createTeam: (TeamData) -> Unit = {},
    panelsSize: Int = 360,
    columns: Int = 1,
    canDeleteTeam: Boolean = false,
    canCreateTeam: Boolean = false,
    selectedTeamId: Long = 0,
    markSelectedTeamId: Boolean = false
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(columns),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(teams){ team ->
            TeamPanel(
                teamData = team,
                modifier = Modifier
                    .padding(16.dp)
                    .width(panelsSize.dp),
                imageSize = 80,
                spacedBy = 8,
                onTeamCardClicked = selectTeam,
                hasDeleteButton = canDeleteTeam,
                onDeleteTeamClicked = deleteTeam,
                onCatClicked = {},
                markTeam = markSelectedTeamId && (selectedTeamId == team.teamId),
            )
        }
        if(canCreateTeam){
            item {
                /* New Team TeamPanel */
                TeamPanel(
                    teamData = TeamData(
                        teamId = 0,
                        teamName = "Create New Team",
                        cats = listOf()
                    ),
                    modifier = Modifier
                        .padding(16.dp)
                        .width(panelsSize.dp),
                    imageSize = 80,
                    spacedBy = 8,
                    onTeamCardClicked = createTeam,
                    onDeleteTeamClicked = { },
                    onCatClicked = {},
                )
            }
        }
    }
}