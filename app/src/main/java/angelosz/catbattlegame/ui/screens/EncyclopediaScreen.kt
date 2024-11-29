package angelosz.catbattlegame.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
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
import angelosz.catbattlegame.domain.models.CatDetailsData
import angelosz.catbattlegame.domain.models.entities.Cat
import angelosz.catbattlegame.ui.components.CatDataDetailsCard
import angelosz.catbattlegame.ui.components.CatSmallDataCard
import angelosz.catbattlegame.ui.viewmodels.CatEncyclopediaViewModel

@Composable
fun EncyclopediaScreen(
    windowSize: WindowWidthSizeClass,
    onBackPressed: () -> Unit
){
    BackHandler( onBack = onBackPressed )

    val viewModel: CatEncyclopediaViewModel = viewModel( factory = CatViewModelProvider.Factory )
    val uiState by viewModel.uiState.collectAsState()

    Scaffold { innerPadding ->
        if(windowSize == WindowWidthSizeClass.Expanded){
            LandscapeEncyclopediaScreen(
                modifier = Modifier.padding(innerPadding),
                cats = uiState.cats,
                catDetails = uiState.selectedCatData,
                onCardClicked = { id ->
                    viewModel.updateCardDetails(id)
                }
            )
        } else {
            PortraitEncyclopediaScreen(
                modifier = Modifier.padding(innerPadding),
                cats = uiState.cats,
                catDetails = uiState.selectedCatData,
                onCardClicked = { id ->
                    viewModel.updateCardDetails(id)
                    viewModel.changeView(toDetailView = true)
                },
                isOnDetailsView = uiState.onDetailsView,
                onDetailsBackClicked = { viewModel.changeView(toDetailView = false) }
            )
        }
    }
}

@Composable
private fun PortraitEncyclopediaScreen(
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
            painter = painterResource(R.drawable.encyclopedia_portrait),
            contentDescription = "",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        if(!isOnDetailsView){
            LazyVerticalGrid(
                columns = GridCells.FixedSize(160.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                items(cats) { cat ->
                    CatSmallDataCard(
                        modifier = Modifier.padding(8.dp),
                        id = cat.id,
                        name = cat.name,
                        image = cat.image,
                        onCardClicked = onCardClicked
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
private fun LandscapeEncyclopediaScreen(
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
            painter = painterResource(R.drawable.encyclopedia_landscape),
            contentDescription = "",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Row(
            modifier = Modifier.fillMaxSize()
        ) {
            LazyVerticalGrid(
                modifier = Modifier.weight(1f),
                columns = GridCells.FixedSize(160.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                items(cats) { cat ->
                    CatSmallDataCard(
                        modifier = Modifier.padding(8.dp),
                        id = cat.id,
                        name = cat.name,
                        image = cat.image,
                        onCardClicked = onCardClicked
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