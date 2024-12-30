package angelosz.catbattlegame.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import angelosz.catbattlegame.CatViewModelProvider
import angelosz.catbattlegame.R
import angelosz.catbattlegame.ui.components.BackgroundImage
import angelosz.catbattlegame.ui.components.RoundedImageButton


@Composable
fun HomeScreen(
    windowSize: WindowWidthSizeClass,
    onPlayButtonClick: () -> Unit,
    navigateToEncyclopedia: () -> Unit,
    navigateToCollection: () -> Unit,
    navigateToBattleChests: () -> Unit,
    navigateToTeamBuild: () -> Unit
) {
    val isPortraitView = windowSize != WindowWidthSizeClass.Expanded
    val background = if(isPortraitView) R.drawable.homescreen_portrait else R.drawable.homescreen_landscape

    val viewModel:HomeScreenViewModel = viewModel(factory = CatViewModelProvider.Factory)
    val uiState by viewModel.uiState.collectAsState()

    Box(modifier = Modifier
        .fillMaxSize(),
    ){
        BackgroundImage(background)
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            if(isPortraitView){
                Spacer(modifier = Modifier.fillMaxSize().weight(0.2f))
                Image(
                    painter = painterResource(R.mipmap.ic_launcher_foreground),
                    contentDescription = "",
                    modifier = Modifier.fillMaxSize().weight(0.2f),
                )
                Spacer(modifier = Modifier.fillMaxSize().weight(0.2f))
                HomeButtons(
                    navigateToCollection = navigateToCollection,
                    navigateToEncyclopedia = navigateToEncyclopedia,
                    onPlayButtonClick = onPlayButtonClick
                )
                Spacer(modifier = Modifier.fillMaxSize().weight(0.1f))
            } else {
                Image(
                    painter = painterResource(R.mipmap.ic_launcher_foreground),
                    contentDescription = "",
                    modifier = Modifier.size(192.dp)
                )
                HomeButtons(
                    navigateToCollection = navigateToCollection,
                    navigateToEncyclopedia = navigateToEncyclopedia,
                    onPlayButtonClick = onPlayButtonClick
                )
            }
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .align(Alignment.TopStart)
                .systemBarsPadding()
        ){
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.padding(start = 16.dp)
            ){
                Image(
                    painter = painterResource(R.drawable.goldcoins_128),
                    contentDescription = "Gold coins",
                    modifier = Modifier.size(32.dp)
                )
                Text(
                    text = "${uiState.gold}",
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.White
                )
                Spacer(modifier = Modifier.width(12.dp))
                Image(
                    painter = painterResource(R.drawable.crystals_128),
                    contentDescription = "Crystals",
                    modifier = Modifier.size(32.dp)
                )
                Text(
                    text = "${uiState.crystals}",
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.White
                )
            }
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 8.dp),
        ) {
            Column(){
                RoundedImageButton(
                    onClick = navigateToBattleChests,
                    innerImage = R.drawable.battlechest_256,
                )
                RoundedImageButton(
                    onClick = navigateToTeamBuild,
                    innerImage = R.drawable.teams_button_256,
                )
            }
        }
    }
}

@Composable
private fun HomeButtons(
    navigateToCollection: () -> Unit,
    navigateToEncyclopedia: () -> Unit,
    onPlayButtonClick: () -> Unit
) {
    HomeButton(
        modifier = Modifier.padding(8.dp),
        image = R.drawable.button_play,
        contentDescription = "Play button",
        onButtonClicked = onPlayButtonClick
    )
    HomeButton(
        modifier = Modifier.padding(8.dp),
        image = R.drawable.button_collection,
        contentDescription = "Collection button",
        onButtonClicked = navigateToCollection
    )
    HomeButton(
        modifier = Modifier.padding(8.dp),
        image = R.drawable.button_encyclopedia,
        contentDescription = "Encyclopedia button",
        onButtonClicked = navigateToEncyclopedia
    )
}