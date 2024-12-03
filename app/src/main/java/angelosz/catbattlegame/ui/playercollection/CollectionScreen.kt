package angelosz.catbattlegame.ui.playercollection

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import angelosz.catbattlegame.CatViewModelProvider
import angelosz.catbattlegame.R
import angelosz.catbattlegame.domain.enums.CollectionView
import angelosz.catbattlegame.domain.models.CatDetailsData
import angelosz.catbattlegame.domain.models.CollectionSmallCardData
import angelosz.catbattlegame.domain.models.OwnedCatDetailsData
import angelosz.catbattlegame.domain.models.collectionNavigationItems
import angelosz.catbattlegame.ui.components.CatDataDetailsCard
import angelosz.catbattlegame.ui.components.CollectionNavigationBottomBar
import angelosz.catbattlegame.ui.components.CollectionNavigationRail
import angelosz.catbattlegame.ui.components.SmallImageCardWithExperienceBar
import angelosz.catbattlegame.ui.viewmodels.CatCollectionViewModel

@Composable
fun CollectionScreen(
    windowSize: WindowWidthSizeClass,
    onBackPressed: () -> Unit
){
    val viewModel: CatCollectionViewModel = viewModel(factory = CatViewModelProvider.Factory)
    val uiState = viewModel.uiState.collectAsState()

    val smallCardsData = uiState.value.smallCardsData
    val selectedCat = uiState.value.selectedCat
    val isDetailView = uiState.value.isDetailView
    val isPortraitView = windowSize != WindowWidthSizeClass.Expanded
    val onBackWasPressed = {
        if(isDetailView) {
            viewModel.deselectCat()
            viewModel.changeView(toDetailView = false)
        } else {
            onBackPressed()
        }
    }

    BackHandler { onBackWasPressed() }

    Scaffold(
        bottomBar = {
            if(isPortraitView){
                CollectionNavigationBottomBar(
                    selectedView = CollectionView.CATS,
                    onTabPressed = {},
                    onBackPressed = onBackWasPressed,
                    items = collectionNavigationItems,
                    modifier = Modifier.height(84.dp)
                )
            }
        }
    ){ innerPadding ->
        if(isPortraitView) {
            HandleCollectionPortraitView(
                cats = smallCardsData,
                selectedCat = selectedCat,
                isDetailView = isDetailView,
                onCardClicked = { id ->
                    viewModel.selectCat( id )
                    viewModel.changeView( toDetailView = true )
                },
                innerPadding = innerPadding
            )
        } else {
            HandleCollectionLandscapeView(
                cats = smallCardsData,
                selectedCat = selectedCat,
                onCardClicked = { id ->
                    viewModel.selectCat( id )
                    viewModel.changeView( toDetailView = true )
                },
                onBackPressed = onBackWasPressed,
                innerPadding = innerPadding,
            )
        }
    }
}


@Composable
private fun HandleCollectionPortraitView(
    cats: List<CollectionSmallCardData>,
    selectedCat: OwnedCatDetailsData?,
    onCardClicked: (Int) -> Unit,
    isDetailView: Boolean,
    innerPadding: PaddingValues
) {
    Box(
        modifier = Modifier.fillMaxSize().padding(innerPadding),
    ) {
        Image(
            painter = painterResource(R.drawable.player_collection_portrait_blurry),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
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
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                items(cats) { data ->
                    SmallImageCardWithExperienceBar(
                        modifier = Modifier.padding(16.dp),
                        id = data.id,
                        name = "",
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
private fun HandleCollectionLandscapeView(
    cats: List<CollectionSmallCardData>,
    selectedCat: OwnedCatDetailsData?,
    onCardClicked: (Int) -> Unit,
    innerPadding: PaddingValues,
    onBackPressed: () -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Image(
            painter = painterResource(R.drawable.player_collection_portrait_blurry),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxSize()
        ) {
            CollectionNavigationRail(
                modifier = Modifier.width(48.dp),
                selectedView = CollectionView.CATS,
                items = collectionNavigationItems,
                onTabPressed = {},
                onBackPressed = onBackPressed,
            )
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
                            modifier = Modifier,
                            id = data.id,
                            name = "",
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
