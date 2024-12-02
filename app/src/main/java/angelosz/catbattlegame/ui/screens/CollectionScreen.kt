package angelosz.catbattlegame.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Surface
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
import angelosz.catbattlegame.domain.models.CollectionSmallCardData
import angelosz.catbattlegame.domain.models.OwnedCatDetailsData
import angelosz.catbattlegame.ui.components.CollectionDetailsDataCard
import angelosz.catbattlegame.ui.components.CollectionSmallCard
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

    BackHandler {
        if(isDetailView) {
            viewModel.deselectCat()
            viewModel.changeView(toDetailView = false)
        } else {
            onBackPressed()
        }
    }

    Surface(modifier = Modifier.fillMaxSize()){
        if(windowSize != WindowWidthSizeClass.Expanded) {
            HandleCollectionPortraitView(
                cats = smallCardsData,
                selectedCat = selectedCat,
                isDetailView = isDetailView,
                onCardClicked = { id ->
                    viewModel.selectCat( id )
                    viewModel.changeView( toDetailView = true )
                }
            )
        } else {
            HandleCollectionLandscapeView(
                cats = smallCardsData,
                selectedCat = selectedCat,
                onCardClicked = { id ->
                    viewModel.selectCat( id )
                    viewModel.changeView( toDetailView = true )
                }
            )
        }
    }
}


@Composable
private fun HandleCollectionPortraitView(
    cats: List<CollectionSmallCardData>,
    selectedCat: OwnedCatDetailsData?,
    onCardClicked: (Int) -> Unit,
    isDetailView: Boolean
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
        if(isDetailView){
            selectedCat?.let { cat ->
                CollectionDetailsDataCard(cat)
            }
                ?: Text( text = "No cat selected")
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(cats) { data ->
                    CollectionSmallCard(
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
            modifier = Modifier.fillMaxSize()
        ) {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(160.dp),
                modifier = Modifier.fillMaxWidth().weight(1f),
                horizontalArrangement = Arrangement.Center
            ) {
                items(cats) { data ->
                    CollectionSmallCard(
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
            selectedCat?.let { cat ->
                CollectionDetailsDataCard(
                    catDetails = cat,
                    modifier = Modifier
                        .padding(4.dp)
                        .width(384.dp)
                        .fillMaxHeight(),
                )
            }
        }
    }
}
