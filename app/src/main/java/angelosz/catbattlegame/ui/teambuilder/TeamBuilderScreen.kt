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
import angelosz.catbattlegame.domain.models.OwnedCatDetailsData
import angelosz.catbattlegame.ui.components.BackgroundImage
import angelosz.catbattlegame.ui.components.CatImageCardGrid
import angelosz.catbattlegame.ui.components.PaginationButtons
import angelosz.catbattlegame.ui.components.SmallImageCard

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
        if(isPortraitView){
            Column(
                modifier = Modifier
                    .systemBarsPadding()
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if(!uiState.teamIsSelected){
                    TeamsList(
                        teams = uiState.teams,
                        selectTeam = { teamData -> viewModel.selectTeam(teamData.teamId) },
                        deleteTeam = { teamData -> viewModel.deleteTeam(teamData.teamId) },
                        createTeam = { _ -> viewModel.createNewTeam() }
                    )
                } else {
                    BackHandler { viewModel.goBackToTeamList() }

                    TeamCatsGridPage(uiState, viewModel)

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
                        onDeleteTeamClicked = { teamData -> viewModel.deleteTeam(teamData.teamId)},
                        onCatClicked = { catId -> viewModel.removeCatFromSelectedTeam(catId) }
                    )
                }
            }
        }
    }
}

@Composable
private fun SelectedCatInfoCard(
    selectedCat: OwnedCatDetailsData,
    onAddClicked: () -> Unit,
) {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .height(208.dp),
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
                SmallImageCard(
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

                }
            }
        }
    }
}

@Composable
private fun TeamCatsGridPage(
    uiState: TeamBuilderUiState,
    viewModel: TeamBuilderViewModel,
) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .height(420.dp)
            .fillMaxWidth(),
        shape = RectangleShape,
    ){
        Column(
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            CatImageCardGrid(
                catsData = uiState.pageCatsData,
                pageLimit = viewModel.paginationLimit,
                imageSize = 112,
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
private fun TeamsList(
    teams: List<TeamData>,
    selectTeam: (TeamData) -> Unit,
    deleteTeam: (TeamData) -> Unit,
    createTeam: (TeamData) -> Unit

) {
    for (team in teams) {
        TeamPanel(
            teamData = team,
            modifier = Modifier
                .padding(16.dp)
                .width(360.dp),
            imageSize = 80,
            spacedBy = 8,
            onTeamCardClicked = selectTeam,
            hasDeleteButton = true,
            onDeleteTeamClicked = deleteTeam,
            onCatClicked = {},
        )
    }
    /* New Team TeamPanel */
    TeamPanel(
        teamData = TeamData(
            teamId = 0,
            teamName = "Create New Team",
            cats = listOf()
        ),
        modifier = Modifier
            .padding(16.dp)
            .width(360.dp),
        imageSize = 80,
        spacedBy = 8,
        onTeamCardClicked = createTeam,
        onDeleteTeamClicked = { },
        onCatClicked = {},
    )
}