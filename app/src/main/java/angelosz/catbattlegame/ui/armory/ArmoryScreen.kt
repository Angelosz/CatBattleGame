package angelosz.catbattlegame.ui.armory

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import angelosz.catbattlegame.CatViewModelProvider
import angelosz.catbattlegame.R
import angelosz.catbattlegame.domain.enums.ScreenState
import angelosz.catbattlegame.ui.armory.cats_view.ArmoryCatsView
import angelosz.catbattlegame.ui.armory.cats_view.ArmoryCatsViewModel
import angelosz.catbattlegame.ui.armory.data.CollectionsNavigationItem
import angelosz.catbattlegame.ui.armory.data.armoryNavigationItems
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
    val uiState by viewModel.uiState.collectAsState()
    val isLandscapeView = windowSize == WindowWidthSizeClass.Expanded

    val onBackButtonPressed = determineBackButtonAction(uiState, armoryCatsViewModel, armoryTeamsViewModel, returnToMainMenu)
    BackHandler(onBack = onBackButtonPressed)

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        BackgroundImage(if(isLandscapeView) R.drawable.encyclopedia_landscape_blurry else R.drawable.encyclopedia_portrait_blurry)
        when(uiState.screenState){
            ScreenState.SUCCESS -> {
                Scaffold(
                    containerColor = Color.Transparent,
                    bottomBar = {
                        if(!isLandscapeView){
                            ArmoryScreenBottomBar(
                                selectedView = uiState.selectedCollection,
                                onTabPressed = viewModel::selectCollection,
                                onBackPressed = onBackButtonPressed,
                                items = armoryNavigationItems
                            )
                        }
                    },
                    content = { innerPadding ->
                        if(isLandscapeView) ArmoryScreenNavigationRail(
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
                            CollectionsView.BATTLE_CHESTS -> { Text("Armory Battle Chests View") }
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
    returnToMainMenu: () -> Unit,
): () -> Unit = {
    when (uiState.selectedCollection) {
        CollectionsView.CATS -> if (armoryCatsViewModel.uiState.value.isDetailView) armoryCatsViewModel.exitDetailView() else returnToMainMenu()
        CollectionsView.TEAMS -> {
            if (armoryTeamsViewModel.hasTeamSelected()) {
                armoryTeamsViewModel.saveTeam()
                armoryTeamsViewModel.deselectTeam()
            } else returnToMainMenu()
        }

        CollectionsView.BATTLE_CHESTS -> returnToMainMenu()
        else -> returnToMainMenu()
    }
}

@Composable
fun ArmoryScreenBottomBar(
    modifier: Modifier = Modifier,
    selectedView: CollectionsView,
    onTabPressed:(CollectionsView) -> Unit,
    hasBackButton: Boolean = true,
    onBackPressed: () -> Unit,
    items: List<CollectionsNavigationItem> = armoryNavigationItems
) {
    NavigationBar(
        modifier = modifier
    ){
        if(hasBackButton){
            NavigationBarItem(
                selected = false,
                onClick = onBackPressed,
                icon = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back Button"
                    )
                }
            )
        }
        for( item in items ){
            NavigationBarItem(
                selected = item.view == selectedView,
                onClick = { onTabPressed(item.view) },
                icon = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            modifier = Modifier.size(32.dp),
                            painter = painterResource(item.icon),
                            contentDescription = item.title
                        )
                        Text(
                            text = item.title,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            )
        }
    }
}

@Composable
fun ArmoryScreenNavigationRail(
    modifier: Modifier = Modifier,
    selectedView: CollectionsView,
    onTabPressed:(CollectionsView) -> Unit,
    hasBackButton: Boolean = true,
    onBackPressed: () -> Unit,
    items: List<CollectionsNavigationItem> = armoryNavigationItems
){
    NavigationRail(
        modifier = modifier,
    ){
        if(hasBackButton){
            NavigationRailItem(
                selected = false,
                onClick = onBackPressed,
                icon = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back button"
                    )
                }
            )
        }

        for( item in items ){
            NavigationRailItem(
                modifier = Modifier
                    .padding(vertical = 16.dp),
                selected = item.view == selectedView,
                onClick = { onTabPressed(item.view) },
                icon = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            modifier = Modifier.size(48.dp),
                            painter = painterResource(item.icon),
                            contentDescription = item.title
                        )
                        Text(
                            text = item.title,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ArmoryScreenBottomBarPreview(){
    Scaffold(
        bottomBar = {
            ArmoryScreenBottomBar(
                selectedView = CollectionsView.CATS,
                onTabPressed = {},
                onBackPressed = {},
                )
        },
        content = { innerPadding ->
            Text(
                text = "Content",
                modifier = Modifier.padding(innerPadding),
            )
        }
    )
}