package angelosz.catbattlegame.ui.teamselection

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
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
import angelosz.catbattlegame.ui.components.BackgroundImage
import angelosz.catbattlegame.ui.components.FailureCard
import angelosz.catbattlegame.ui.components.LoadingCard
import angelosz.catbattlegame.ui.teambuilder.TeamsList

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

    Box(
        contentAlignment = Alignment.Center
    ){
        BackgroundImage(R.drawable.encyclopedia_landscape_blurry)

        when(uiState.screenState){
            ScreenState.SUCCESS -> {
                if(isPortraitView){
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
                            selectedTeamId = uiState.teamId,
                            markSelectedTeamId = true
                        )
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.BottomCenter
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
            ScreenState.WORKING -> {

            }
        }
    }
}