package angelosz.catbattlegame.ui.combat.teamselection

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
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
import angelosz.catbattlegame.utils.SoundManager

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
                    Text(
                        text = stringResource(R.string.choose_team),
                        style = MaterialTheme.typography.headlineMedium.copy(
                            shadow = Shadow(
                                color = Color.Black,
                                offset = Offset(2f, 2f),
                                blurRadius = 4f
                            )
                        ),
                        color = Color(0xFFFFD54F),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                    )

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
                            .height(118.dp)
                            .padding(bottom = 24.dp)
                            .align(Alignment.BottomCenter),
                        contentAlignment = Alignment.Center
                    ){
                        ChooseTeamButton(
                            onClick = { onStartFightClicked(chapterId, uiState.teamId) }
                        )
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
fun ChooseTeamButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val context = LocalContext.current
    Button(
        modifier = modifier.height(56.dp),
        onClick = {
            SoundManager.playButtonClick(context)
            onClick()
        },
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF6E4327)
        ),
        border = BorderStroke(3.dp, Color(0xFFFFD700)),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 2.dp),
            text = stringResource(R.string.choose_team),
            style = MaterialTheme.typography.titleLarge.copy(
                shadow = Shadow(
                    color = Color.Black,
                    offset = Offset(2f, 2f),
                    blurRadius = 4f
                )
            ),
            color = Color(0xFFFFF8E1),
            fontWeight = FontWeight.Bold
        )
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
                        teamName = stringResource(R.string.create_new_team),
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
                        .height(118.dp),
                    contentAlignment = Alignment.Center
                ) {
                    ChooseTeamButton(onClick = onChooseTeamClicked)
                }
            }
        } else {
            item {
                Spacer(modifier = Modifier.height(84.dp))
            }
            item {
                Spacer(modifier = Modifier.height(84.dp))
            }
        }
    }
}