package angelosz.catbattlegame.ui.armory

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import angelosz.catbattlegame.CatViewModelProvider
import angelosz.catbattlegame.R
import angelosz.catbattlegame.domain.enums.ScreenState
import angelosz.catbattlegame.ui.armory.battlechest_view.ArmoryBattleChestView
import angelosz.catbattlegame.ui.armory.battlechest_view.ArmoryBattleChestViewModel
import angelosz.catbattlegame.ui.armory.cats_view.ArmoryCatsView
import angelosz.catbattlegame.ui.armory.cats_view.ArmoryCatsViewModel
import angelosz.catbattlegame.ui.armory.composables.CollectionsBottomBar
import angelosz.catbattlegame.ui.armory.composables.CollectionsNavigationRail
import angelosz.catbattlegame.ui.armory.data.armoryNavigationItems
import angelosz.catbattlegame.ui.armory.enums.ArmoryBattleChestStage
import angelosz.catbattlegame.ui.armory.enums.CollectionsView
import angelosz.catbattlegame.ui.armory.teams_view.ArmoryTeamsView
import angelosz.catbattlegame.ui.armory.teams_view.ArmoryTeamsViewModel
import angelosz.catbattlegame.ui.components.BackgroundImage
import angelosz.catbattlegame.ui.components.FailureCard
import angelosz.catbattlegame.ui.components.LoadingCard


@Composable
fun ArmoryScreen(
    windowSize: WindowWidthSizeClass,
    returnToMainMenu: () -> Unit,
    viewModel: ArmoryScreenViewModel = viewModel(factory = CatViewModelProvider.Factory),
    selectCollection: CollectionsView = CollectionsView.CATS
) {
    val armoryCatsViewModel: ArmoryCatsViewModel = viewModel(factory = CatViewModelProvider.Factory)
    val armoryTeamsViewModel: ArmoryTeamsViewModel = viewModel(factory = CatViewModelProvider.Factory)
    val armoryBattleChestViewModel: ArmoryBattleChestViewModel = viewModel(factory = CatViewModelProvider.Factory)
    val uiState by viewModel.uiState.collectAsState()
    val isLandscapeView = windowSize == WindowWidthSizeClass.Expanded

    val onBackButtonPressed = determineBackButtonAction(
        uiState = uiState,
        armoryCatsViewModel = armoryCatsViewModel,
        armoryTeamsViewModel =  armoryTeamsViewModel,
        armoryBattleChestViewModel = armoryBattleChestViewModel,
        returnToMainMenu = {
            if (armoryTeamsViewModel.hasTeamSelected()) {
                armoryTeamsViewModel.saveTeam()
            }
            returnToMainMenu()
        },
    )
    BackHandler(onBack = onBackButtonPressed)

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        BackgroundImage(if(isLandscapeView) R.drawable.player_collection_landscape_blurred else R.drawable.player_collection_portrait_blurry)
        when(uiState.screenState){
            ScreenState.SUCCESS -> {
                Scaffold(
                    containerColor = Color.Transparent,
                    bottomBar = {
                        if(!isLandscapeView){
                            CollectionsBottomBar(
                                selectedView = uiState.selectedCollection,
                                onTabPressed = viewModel::selectCollection,
                                onBackPressed = onBackButtonPressed,
                                items = armoryNavigationItems
                            )
                        }
                    },
                    content = { innerPadding ->
                        if(isLandscapeView) CollectionsNavigationRail(
                            selectedView = uiState.selectedCollection,
                            onTabPressed = viewModel::selectCollection,
                            onBackPressed = onBackButtonPressed,
                            items = armoryNavigationItems
                        )
                        when(uiState.selectedCollection){
                            CollectionsView.CATS -> {
                                ArmoryCatsView(
                                    viewModel = armoryCatsViewModel,
                                    innerPadding = innerPadding,
                                    onFailure = returnToMainMenu,
                                    isLandscapeView = isLandscapeView
                                )
                            }
                            CollectionsView.TEAMS -> {
                                ArmoryTeamsView(
                                    viewModel = armoryTeamsViewModel,
                                    innerPadding = innerPadding,
                                    onFailure = returnToMainMenu,
                                    isLandscapeView = isLandscapeView
                                )
                            }
                            CollectionsView.BATTLE_CHESTS -> {
                                ArmoryBattleChestView(
                                    viewModel = armoryBattleChestViewModel,
                                    innerPadding = innerPadding,
                                    onFailure = returnToMainMenu,
                                    onBattleChestOpened = {
                                        armoryCatsViewModel.reloadDataIfAlreadyInitialized()
                                        armoryTeamsViewModel.reloadDataIfAlreadyInitialized()
                                    },
                                    isLandscapeView = isLandscapeView
                                )
                            }
                            else -> { FailureCard(returnToMainMenu) }
                        }
                    }
                )
            }
            ScreenState.LOADING -> {
                LoadingCard()
            }
            ScreenState.FAILURE -> {
                FailureCard(returnToMainMenu)
            }
            ScreenState.INITIALIZING -> {
                LoadingCard()
                LaunchedEffect(true) {
                    viewModel.setup(selectCollection)
                }
            }
        }
    }
}

@Composable
private fun determineBackButtonAction(
    uiState: ArmoryUiState,
    armoryCatsViewModel: ArmoryCatsViewModel,
    armoryTeamsViewModel: ArmoryTeamsViewModel,
    armoryBattleChestViewModel: ArmoryBattleChestViewModel,
    returnToMainMenu: () -> Unit,
): () -> Unit = {
    when (uiState.selectedCollection) {
        CollectionsView.CATS ->{
            if (armoryCatsViewModel.uiState.value.isDetailView)
                armoryCatsViewModel.exitDetailView()
            else returnToMainMenu()
        }
        CollectionsView.TEAMS -> {
            if (armoryTeamsViewModel.hasTeamSelected()) {
                armoryTeamsViewModel.saveTeam()
                armoryTeamsViewModel.deselectTeam()
            } else returnToMainMenu()
        }

        CollectionsView.BATTLE_CHESTS -> {
            when(armoryBattleChestViewModel.getView()){
                ArmoryBattleChestStage.GRID -> returnToMainMenu()
                ArmoryBattleChestStage.BATTLE_CHEST -> armoryBattleChestViewModel.returnToGrid()
                ArmoryBattleChestStage.CAT -> armoryBattleChestViewModel.reloadBattleChests()
            }
        }
        else -> returnToMainMenu()
    }
}