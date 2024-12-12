package angelosz.catbattlegame.ui.playercollection

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import angelosz.catbattlegame.CatViewModelProvider
import angelosz.catbattlegame.R
import angelosz.catbattlegame.domain.enums.CollectionView
import angelosz.catbattlegame.domain.enums.ScreenState
import angelosz.catbattlegame.domain.models.CatDetailsData
import angelosz.catbattlegame.domain.models.CollectionSmallCardData
import angelosz.catbattlegame.domain.models.OwnedCatDetailsData
import angelosz.catbattlegame.domain.models.collectionNavigationItems
import angelosz.catbattlegame.ui.components.BackgroundImage
import angelosz.catbattlegame.ui.components.CatDataDetailsCard
import angelosz.catbattlegame.ui.components.CollectionNavigationBottomBar
import angelosz.catbattlegame.ui.components.CollectionNavigationRail
import angelosz.catbattlegame.ui.components.FailureCard
import angelosz.catbattlegame.ui.components.LoadingCard
import angelosz.catbattlegame.ui.components.SmallImageCardWithExperienceBar

@Composable
fun CollectionScreen(
    windowSize: WindowWidthSizeClass,
    onBackPressed: () -> Unit
){
    val viewModel: CollectionViewModel = viewModel(factory = CatViewModelProvider.Factory)
    val uiState by viewModel.uiState.collectAsState()

    val isPortraitView = windowSize != WindowWidthSizeClass.Expanded

    val onBackWasPressed = {
        if(uiState.isDetailView) {
            viewModel.changeView(toDetailView = false)
        } else {
            onBackPressed()
        }
    }

    BackHandler(onBack = onBackWasPressed)
    when(uiState.screenState){
        ScreenState.SUCCESS -> {
            Scaffold(
                bottomBar = {
                    if(isPortraitView){
                        CollectionNavigationBottomBar(
                            selectedView = CollectionView.CATS,
                            onTabPressed = {  },
                            onBackPressed = onBackWasPressed,
                            items = collectionNavigationItems,
                        )
                    }
                }
            ){ innerPadding ->
                if(isPortraitView) {
                    HandleCollectionPortraitView(
                        viewModel = viewModel,
                        uiState = uiState,
                        innerPadding = innerPadding,
                    )
                } else {
                    HandleCollectionLandscapeView(
                        viewModel = viewModel,
                        uiState = uiState,
                        innerPadding = innerPadding,
                        onBackWasPressed = onBackWasPressed
                    )
                }
            }
        }
        ScreenState.LOADING -> {
            LoadingCard()
        }
        ScreenState.FAILURE -> {
            FailureCard(
                onBackPressed = onBackWasPressed,
                onReloadPressed = { viewModel.setupInitialData() }

            )
        }
        ScreenState.WORKING -> { }
    }
}


@Composable
private fun HandleCollectionPortraitView(
    innerPadding: PaddingValues,
    uiState: CollectionUiState,
    viewModel: CollectionViewModel
) {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxSize()
    ) {
        when(uiState.collectionView){
            CollectionView.CATS -> {
                CatCollectionPortraitView(
                    cats = uiState.smallCardsData,
                    selectedCat = uiState.selectedCat,
                    onCardClicked = { id ->
                        viewModel.selectCat( id )
                        viewModel.changeView( toDetailView = true )
                    },
                    isDetailView = uiState.isDetailView,
                    modifier = Modifier.padding(innerPadding),
                )
            }
            else -> {

            }
        }
    }
}

@Composable
private fun HandleCollectionLandscapeView(
    innerPadding: PaddingValues,
    uiState: CollectionUiState,
    viewModel: CollectionViewModel,
    onBackWasPressed: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxSize()
    ) {
        CollectionNavigationRail(
            selectedView = CollectionView.CATS,
            items = collectionNavigationItems,
            onTabPressed = { },
            onBackPressed = onBackWasPressed,
        )
        when(uiState.collectionView){
            CollectionView.CATS -> {
                CatCollectionLandscapeView(
                    cats = uiState.smallCardsData,
                    selectedCat = uiState.selectedCat,
                    onCardClicked = { id ->
                        viewModel.selectCat( id )
                        viewModel.changeView( toDetailView = true )
                    },
                    innerPadding = innerPadding,
                )
            }
            else -> {

            }
        }
    }
}

@Composable
fun CatCollectionPortraitView(
    cats: List<CollectionSmallCardData>,
    selectedCat: OwnedCatDetailsData?,
    onCardClicked: (Int) -> Unit,
    isDetailView: Boolean,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
    ) {
        BackgroundImage(R.drawable.player_collection_portrait_blurry)
        if(isDetailView){
            selectedCat?.let { ownedCat ->
                CatDataDetailsCard(
                    catDetails = CatDetailsData(
                        cat = ownedCat.cat,
                        abilities = ownedCat.abilities,
                        nextEvolutionName = ownedCat.evolutionCat?.second ?: "",
                        isElderOf = ownedCat.isElderOf?.second ?: ""
                    ),
                    showExperienceBar = true,
                    level = ownedCat.level,
                    experience = ownedCat.experience,
                    modifier = Modifier.padding(16.dp)
                )
            }
                ?: Text( text = "No cat selected")
        } else {
            LazyVerticalGrid(
                columns = GridCells.FixedSize(160.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                items(cats) { data ->
                    SmallImageCardWithExperienceBar(
                        modifier = Modifier.padding(8.dp),
                        id = data.id,
                        image = data.image,
                        imageSize = 160,
                        onCardClicked = onCardClicked,
                        level = data.level,
                        experience = data.experience
                    )
                }
            }
        }
    }
}

@Composable
fun CatCollectionLandscapeView(
    cats: List<CollectionSmallCardData>,
    selectedCat: OwnedCatDetailsData?,
    onCardClicked: (Int) -> Unit,
    innerPadding: PaddingValues,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        BackgroundImage(R.drawable.player_collection_portrait_blurry)
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                LazyVerticalGrid(
                    columns = GridCells.FixedSize(192.dp),
                    modifier = Modifier
                        .weight(1f),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    items(cats) { data ->
                        SmallImageCardWithExperienceBar(
                            modifier = Modifier.padding(8.dp),
                            id = data.id,
                            image = data.image,
                            imageSize = 192,
                            onCardClicked = onCardClicked,
                            level = data.level,
                            experience = data.experience
                        )
                    }
                }
                selectedCat?.let { ownedCat ->
                    CatDataDetailsCard(
                        catDetails = CatDetailsData(
                            cat = ownedCat.cat,
                            abilities = ownedCat.abilities,
                            nextEvolutionName = ownedCat.evolutionCat?.second ?: "",
                            isElderOf = ownedCat.isElderOf?.second ?: ""
                        ),
                        showExperienceBar = true,
                        level = ownedCat.level,
                        experience = ownedCat.experience,
                        modifier = Modifier
                            .padding(4.dp)
                            .width(384.dp)
                            .fillMaxHeight(),
                    )
                }
            }
        }
    }
}