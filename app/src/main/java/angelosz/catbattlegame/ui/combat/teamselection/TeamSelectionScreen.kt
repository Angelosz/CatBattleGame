package angelosz.catbattlegame.ui.combat.teamselection

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import angelosz.catbattlegame.CatViewModelProvider
import angelosz.catbattlegame.R
import angelosz.catbattlegame.domain.enums.ScreenState
import angelosz.catbattlegame.ui.combat.TeamData
import angelosz.catbattlegame.ui.combat.TeamPanel
import angelosz.catbattlegame.ui.components.BackButton
import angelosz.catbattlegame.ui.components.BackgroundImage
import angelosz.catbattlegame.ui.components.FailureCard
import angelosz.catbattlegame.ui.components.LoadingCard

@Composable
fun TeamSelectionScreen(
    chapterId: Long,
    windowSize: WindowWidthSizeClass,
    viewModel: TeamSelectionScreenViewModel = viewModel(factory = CatViewModelProvider.Factory),
    onBackPressed: () -> Unit,
    onStartFightClicked: (chapter: Long, team: Long) -> Unit
){
    val uiState by viewModel.uiState.collectAsState()
    val isPortraitView = windowSize != WindowWidthSizeClass.Expanded

    BackHandler(onBack = onBackPressed)

    Box(
        contentAlignment = Alignment.Center
    ){
        BackgroundImage(R.drawable.encyclopedia_landscape_blurry)

        when(uiState.screenState){
            ScreenState.SUCCESS -> {
                Column(
                    modifier = Modifier
                        .systemBarsPadding()
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TeamsList(
                        teams = uiState.teams,
                        selectTeam = { teamData ->
                            viewModel.selectTeam(teamData.teamId)
                                     },
                        onChooseTeamClicked = { onStartFightClicked(chapterId, uiState.teamId) },
                        columns = if(isPortraitView) 1 else 2,
                        selectedTeamId = uiState.teamId,
                        markSelectedTeamId = true,
                        addChooseTeamAsItem = isPortraitView
                    )
                }
                if(!isPortraitView){
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(128.dp)
                            .align(Alignment.BottomCenter),
                        contentAlignment = Alignment.Center
                    ){
                        Button(
                            modifier = Modifier.padding(8.dp),
                            onClick = { onStartFightClicked(chapterId, uiState.teamId) }
                        ){
                            Text(
                                modifier = Modifier.padding(4.dp),
                                text = "Choose Team",
                                style = MaterialTheme.typography.headlineSmall
                            )
                        }
                    }
                }
                BackButton(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(horizontal = 32.dp, vertical = 56.dp),
                    onBackPressed = onBackPressed
                )
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
            ScreenState.INITIALIZING -> {

            }
        }
    }
}

@Composable
fun TeamsList(
    teams: List<TeamData>,
    selectTeam: (TeamData) -> Unit = {},
    deleteTeam: (TeamData) -> Unit = {},
    createTeam: (TeamData) -> Unit = {},
    onChooseTeamClicked: () -> Unit = {},
    panelsSize: Int = 360,
    columns: Int = 1,
    canDeleteTeam: Boolean = false,
    canCreateTeam: Boolean = false,
    selectedTeamId: Long = 0,
    markSelectedTeamId: Boolean = false,
    addChooseTeamAsItem: Boolean
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
        if(addChooseTeamAsItem){
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(128.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Button(
                        modifier = Modifier.padding(8.dp),
                        onClick = onChooseTeamClicked
                    ) {
                        Text(
                            modifier = Modifier.padding(4.dp),
                            text = "Choose Team",
                            style = MaterialTheme.typography.headlineSmall
                        )
                    }
                }
            }
        } else {
            item {
                Spacer(modifier = Modifier.height(64.dp))
            }
            item {
                Spacer(modifier = Modifier.height(64.dp))
            }
        }
    }
        }
