package angelosz.catbattlegame.ui.armory.battlechest_view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import angelosz.catbattlegame.CatViewModelProvider
import angelosz.catbattlegame.domain.enums.ScreenState
import angelosz.catbattlegame.ui.components.FailureCard
import angelosz.catbattlegame.ui.components.LoadingCard

@Composable
fun ArmoryBattleChestView(
    viewModel: ArmoryBattleChestViewModel = viewModel(factory = CatViewModelProvider.Factory),
    innerPadding: PaddingValues,
    onFailure: () -> Unit,
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
                    HandleArmoryBattleChestLandscapeView(uiState, viewModel)
                } else {
                    HandleArmoryBattleChestPortraitView(uiState, viewModel)
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
                viewModel.setup()
            }
        }
    }
}

@Composable
fun HandleArmoryBattleChestPortraitView(
    uiState: ArmoryBattleChestUiState,
    viewModel: ArmoryBattleChestViewModel,
) {

}

@Composable
fun HandleArmoryBattleChestLandscapeView(
    uiState: ArmoryBattleChestUiState,
    viewModel: ArmoryBattleChestViewModel,
) {

}