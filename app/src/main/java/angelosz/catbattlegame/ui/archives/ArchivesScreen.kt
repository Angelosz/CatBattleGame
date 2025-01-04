package angelosz.catbattlegame.ui.archives

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
import angelosz.catbattlegame.ui.archives.abilities_view.ArchiveAbilitiesView
import angelosz.catbattlegame.ui.archives.abilities_view.ArchiveAbilitiesViewModel
import angelosz.catbattlegame.ui.archives.cats_view.ArchiveCatsView
import angelosz.catbattlegame.ui.archives.cats_view.ArchiveCatsViewModel
import angelosz.catbattlegame.ui.archives.enemies_view.ArchiveEnemiesView
import angelosz.catbattlegame.ui.archives.enemies_view.ArchiveEnemiesViewModel
import angelosz.catbattlegame.ui.armory.composables.CollectionsBottomBar
import angelosz.catbattlegame.ui.armory.composables.CollectionsNavigationRail
import angelosz.catbattlegame.ui.armory.data.archivesNavigationItems
import angelosz.catbattlegame.ui.armory.enums.CollectionsView
import angelosz.catbattlegame.ui.components.BackgroundImage
import angelosz.catbattlegame.ui.components.FailureCard
import angelosz.catbattlegame.ui.components.LoadingCard


@Composable
fun ArchivesScreen(
    windowSize: WindowWidthSizeClass,
    returnToMainMenu: () -> Unit,
    viewModel: ArchiveScreenViewModel = viewModel(factory = CatViewModelProvider.Factory),
    selectedView: CollectionsView = CollectionsView.CATS
){
    val archiveCatsViewModel: ArchiveCatsViewModel = viewModel(factory = CatViewModelProvider.Factory)
    val archiveAbilitiesViewModel: ArchiveAbilitiesViewModel = viewModel(factory = CatViewModelProvider.Factory)
    val archiveEnemiesViewModel: ArchiveEnemiesViewModel = viewModel(factory = CatViewModelProvider.Factory)
    val uiState by viewModel.uiState.collectAsState()
    val isLandscapeView = windowSize == WindowWidthSizeClass.Expanded


    val onBackButtonPressed = determineArchiveBackButtonAction(
        uiState,
        archiveCatsViewModel,
        archiveAbilitiesViewModel,
        archiveEnemiesViewModel,
        returnToMainMenu,
    )
    BackHandler(onBack = onBackButtonPressed)

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        BackgroundImage(if (isLandscapeView) R.drawable.encyclopedia_landscape_blurry else R.drawable.encyclopedia_portrait_blurry)
        when (uiState.screenState) {
            ScreenState.SUCCESS -> {
                Scaffold(
                    containerColor = Color.Transparent,
                    bottomBar = {
                        if (!isLandscapeView)
                            CollectionsBottomBar(
                                selectedView = uiState.selectedCollection,
                                onTabPressed = viewModel::selectCollection,
                                onBackPressed = onBackButtonPressed,
                                items = archivesNavigationItems
                            )
                    },
                    content = { innerPadding ->
                        if (isLandscapeView)
                            CollectionsNavigationRail(
                                selectedView = uiState.selectedCollection,
                                onTabPressed = viewModel::selectCollection,
                                onBackPressed = onBackButtonPressed,
                                items = archivesNavigationItems
                            )
                        when (uiState.selectedCollection) {
                            CollectionsView.CATS -> {
                                ArchiveCatsView(
                                    viewModel = archiveCatsViewModel,
                                    innerPadding = innerPadding,
                                    onFailure = onBackButtonPressed,
                                    onAbilityClicked = { id ->
                                        if(archiveAbilitiesViewModel.viewModelHasBeenInitialized())
                                            archiveAbilitiesViewModel.selectAbility(id)
                                        else
                                            archiveAbilitiesViewModel.setup(
                                                pageLimit = 12,
                                                selectedAbilityId = id,
                                                showAbility = true
                                            )
                                        viewModel.selectCollection(CollectionsView.ABILITIES)
                                    },
                                    isLandscapeView = isLandscapeView
                                )
                            }

                            CollectionsView.ABILITIES -> {
                                ArchiveAbilitiesView(
                                    viewModel = archiveAbilitiesViewModel,
                                    innerPadding = innerPadding,
                                    onFailure = onBackButtonPressed,
                                    isLandscapeView = isLandscapeView
                                )
                            }

                            CollectionsView.ENEMIES -> {
                                ArchiveEnemiesView(
                                    viewModel = archiveEnemiesViewModel,
                                    innerPadding = innerPadding,
                                    onFailure = onBackButtonPressed,
                                    isLandscapeView = isLandscapeView
                                )
                            }

                            else -> FailureCard(returnToMainMenu)
                        }
                    }
                )

            }

            ScreenState.LOADING -> LoadingCard()
            ScreenState.FAILURE -> FailureCard(returnToMainMenu)
            ScreenState.INITIALIZING -> {
                LoadingCard()
                LaunchedEffect(viewModel) {
                    viewModel.setup(selectedView)
                }
            }
        }
    }
}

fun determineArchiveBackButtonAction(
    uiState: ArchivesUiState,
    archiveCatsViewModel: ArchiveCatsViewModel,
    archiveAbilitiesViewModel: ArchiveAbilitiesViewModel,
    archiveEnemiesViewModel: ArchiveEnemiesViewModel,
    returnToMainMenu: () -> Unit,
): () -> Unit = {
    when (uiState.selectedCollection) {
        CollectionsView.CATS -> {
            if(archiveCatsViewModel.isCatSelected()) archiveCatsViewModel.returnToCatList()
            else returnToMainMenu()
        }
        CollectionsView.ABILITIES -> {
            if(archiveAbilitiesViewModel.isAbilitySelected()) archiveAbilitiesViewModel.returnToAbilityList()
            else returnToMainMenu()
        }
        CollectionsView.ENEMIES -> {
            if(archiveEnemiesViewModel.isEnemySelected()) archiveEnemiesViewModel.returnToEnemyList()
            else returnToMainMenu()
        }
        else -> returnToMainMenu()
    }
}


