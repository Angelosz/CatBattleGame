package angelosz.catbattlegame.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import angelosz.catbattlegame.CatViewModelProvider
import angelosz.catbattlegame.R
import angelosz.catbattlegame.domain.enums.CollectionView
import angelosz.catbattlegame.domain.models.CatDetailsData
import angelosz.catbattlegame.domain.models.entities.Ability
import angelosz.catbattlegame.domain.models.entities.Cat
import angelosz.catbattlegame.ui.components.CatAbilityDetailsCard
import angelosz.catbattlegame.ui.components.CatDataDetailsCard
import angelosz.catbattlegame.ui.components.CollectionNavigationBottomBar
import angelosz.catbattlegame.ui.components.CollectionNavigationRail
import angelosz.catbattlegame.ui.components.SmallDataCard
import angelosz.catbattlegame.ui.viewmodels.CatEncyclopediaUiState
import angelosz.catbattlegame.ui.viewmodels.CatEncyclopediaViewModel

@Composable
fun EncyclopediaScreen(
    windowSize: WindowWidthSizeClass,
    onBackPressed: () -> Unit
){
    BackHandler( onBack = onBackPressed )

    val viewModel: CatEncyclopediaViewModel = viewModel( factory = CatViewModelProvider.Factory )
    val uiState by viewModel.uiState.collectAsState()

    val isLandscape = windowSize == WindowWidthSizeClass.Expanded

    Scaffold(
        bottomBar = {
            if (!isLandscape) {
                AddEncyclopediaBottomNavigationBar(uiState, viewModel, onBackPressed)
            }
        }
    ) { innerPadding ->
        if(isLandscape){
            HandleEncyclopediaLandscapeView(uiState, viewModel, onBackPressed, innerPadding)
        } else {
            HandleEncyclopediaPortraitView(uiState, viewModel, innerPadding,)
        }
    }
}

@Composable
private fun HandleEncyclopediaPortraitView(
    uiState: CatEncyclopediaUiState,
    viewModel: CatEncyclopediaViewModel,
    innerPadding: PaddingValues,
) {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxSize()
    ) {
        when (uiState.collectionView) {
            CollectionView.CATS -> {
                PortraitEncyclopediaCatsScreen(
                    modifier = Modifier.padding(innerPadding),
                    cats = uiState.cats,
                    catDetails = uiState.selectedCatData,
                    onCardClicked = { id ->
                        viewModel.updateSelectedCat(id)
                        viewModel.changeView(toDetailView = true)
                    },
                    isOnDetailsView = uiState.onDetailsView,
                    onDetailsBackClicked = { viewModel.changeView(toDetailView = false) }
                )
            }

            CollectionView.ABILITIES -> {
                PortraitEncyclopediaAbilityScreen(
                    modifier = Modifier.padding(innerPadding),
                    abilities = uiState.abilities,
                    abilityData = uiState.selectedAbility,
                    onCardClicked = { id ->
                        viewModel.updateSelectedAbility(id)
                        viewModel.changeView(toDetailView = true)
                    },
                    isOnDetailsView = uiState.onDetailsView,
                    onDetailsBackClicked = { viewModel.changeView(toDetailView = false) }

                )
            }

            CollectionView.ITEMS -> Text("No items yet :(")
        }
    }
}

@Composable
private fun HandleEncyclopediaLandscapeView(
    uiState: CatEncyclopediaUiState,
    viewModel: CatEncyclopediaViewModel,
    onBackPressed: () -> Unit,
    innerPadding: PaddingValues,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxSize()
    ) {
        AddEncyclopediaNavigationRail(uiState, viewModel, onBackPressed)

        when (uiState.collectionView) {
            CollectionView.CATS -> {
                LandscapeEncyclopediaCatsScreen(
                    modifier = Modifier.padding(innerPadding),
                    cats = uiState.cats,
                    catDetails = uiState.selectedCatData,
                    onCardClicked = { id ->
                        viewModel.updateSelectedCat(id)
                    }
                )
            }

            CollectionView.ABILITIES -> {
                LandscapeEncyclopediaAbilitiesScreen(
                    modifier = Modifier.padding(innerPadding),
                    abilities = uiState.abilities,
                    abilityData = uiState.selectedAbility,
                    onCardClicked = { id ->
                        viewModel.updateSelectedAbility(id)
                    }
                )
            }

            CollectionView.ITEMS -> Text("No items yet :(")
        }

    }
}

@Composable
private fun AddEncyclopediaNavigationRail(
    uiState: CatEncyclopediaUiState,
    viewModel: CatEncyclopediaViewModel,
    onBackPressed: () -> Unit,
) {
    CollectionNavigationRail(
        modifier = Modifier.width(48.dp),
        selectedView = uiState.collectionView,
        onTabPressed = { collectionView ->
            viewModel.updateCollectionView(collectionView)
        },
        onBackPressed = {
            if (uiState.selectedCatData != null || uiState.selectedAbility != null) {
                viewModel.deselectAllData()
                viewModel.changeView(false)
            } else {
                onBackPressed()
            }
        }
    )
}

@Composable
private fun AddEncyclopediaBottomNavigationBar(
    uiState: CatEncyclopediaUiState,
    viewModel: CatEncyclopediaViewModel,
    onBackPressed: () -> Unit,
) {
    CollectionNavigationBottomBar(
        selectedView = uiState.collectionView,
        onTabPressed = { collectionView ->
            viewModel.updateCollectionView(collectionView)
            viewModel.changeView(false)
        },
        onBackPressed = {
            if (uiState.onDetailsView) {
                viewModel.changeView(false)
            } else {
                onBackPressed()
            }
        },
        modifier = Modifier.height(84.dp)
    )
}


@Composable
private fun PortraitEncyclopediaCatsScreen(
    modifier: Modifier = Modifier,
    cats: List<Cat>,
    catDetails: CatDetailsData?,
    onCardClicked: (Int) -> Unit,
    isOnDetailsView: Boolean,
    onDetailsBackClicked: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize(),
    ) {
        Image(
            painter = painterResource(R.drawable.encyclopedia_portrait_blurry),
            contentDescription = "",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
        )
        if(!isOnDetailsView){
            LazyVerticalGrid(
                columns = GridCells.FixedSize(128.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                items(cats) { cat ->
                    SmallDataCard(
                        modifier = Modifier.padding(8.dp),
                        id = cat.id,
                        name = cat.name,
                        image = cat.image,
                        onCardClicked = onCardClicked,
                        imageSize = 128
                    )
                }
            }
        } else {
            catDetails?.let{CatDataDetailsCard(
                modifier = Modifier.padding(16.dp),
                catDetails = catDetails
            )} ?: Text(
                text = "No cat selected :(",
                color = Color.White,
                style = MaterialTheme.typography.displayLarge
            )
            BackHandler {
                onDetailsBackClicked()
            }
        }
    }
}

@Composable
private fun LandscapeEncyclopediaCatsScreen(
    modifier: Modifier,
    cats: List<Cat>,
    catDetails: CatDetailsData?,
    onCardClicked: (Int) -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize(),
    ) {
        Image(
            painter = painterResource(R.drawable.encyclopedia_landscape_blurry),
            contentDescription = "",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Row(
            modifier = Modifier.fillMaxSize()
        ) {
            LazyVerticalGrid(
                modifier = Modifier.weight(1f),
                columns = GridCells.FixedSize(128.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                items(cats) { cat ->
                    SmallDataCard(
                        modifier = Modifier.padding(8.dp),
                        id = cat.id,
                        name = cat.name,
                        image = cat.image,
                        onCardClicked = onCardClicked,
                        imageSize = 128
                    )
                }
            }
            catDetails?.let { details ->
                CatDataDetailsCard(
                    modifier = Modifier
                        .padding(4.dp)
                        .width(384.dp)
                        .fillMaxHeight(),
                    catDetails = details
                )
            }
        }
    }
}

@Composable
private fun PortraitEncyclopediaAbilityScreen(
    modifier: Modifier,
    abilities: List<Ability>,
    abilityData: Ability?,
    onCardClicked: (Int) -> Unit,
    isOnDetailsView: Boolean,
    onDetailsBackClicked: () -> Unit
){
    Box(
        modifier = modifier
            .fillMaxSize(),
    ) {
        Image(
            painter = painterResource(R.drawable.encyclopedia_portrait_blurry),
            contentDescription = "",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        if(!isOnDetailsView){
            LazyVerticalGrid(
                columns = GridCells.FixedSize(128.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                items(abilities) { ability ->
                    SmallDataCard(
                        modifier = Modifier.padding(8.dp),
                        id = ability.id,
                        name = ability.name,
                        image = ability.image,
                        onCardClicked = onCardClicked,
                        imageSize = 128

                    )
                }
            }
        } else {
            abilityData?.let{
                CatAbilityDetailsCard(
                modifier = Modifier.padding(16.dp),
                ability = it
            )
            } ?: Text(
                text = "No ability selected :(",
                color = Color.White,
                style = MaterialTheme.typography.displayLarge
            )
            BackHandler {
                onDetailsBackClicked()
            }
        }
    }
}
@Composable
private fun LandscapeEncyclopediaAbilitiesScreen(
    modifier: Modifier,
    abilities: List<Ability>,
    abilityData: Ability?,
    onCardClicked: (Int) -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize(),
    ) {
        Image(
            painter = painterResource(R.drawable.encyclopedia_landscape_blurry),
            contentDescription = "",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Row(
            modifier = Modifier.fillMaxSize()
        ) {
            LazyVerticalGrid(
                modifier = Modifier.weight(1f),
                columns = GridCells.FixedSize(128.dp),
            ) {
                items(abilities) { ability ->
                    SmallDataCard(
                        modifier = Modifier.padding(8.dp),
                        id = ability.id,
                        name = ability.name,
                        image = ability.image,
                        onCardClicked = onCardClicked,
                        imageSize = 128
                    )
                }
            }
            abilityData?.let {
                CatAbilityDetailsCard(
                    modifier = Modifier
                        .padding(4.dp)
                        .width(384.dp)
                        .fillMaxHeight(),
                    ability = it
                )
            }
        }
    }
}



