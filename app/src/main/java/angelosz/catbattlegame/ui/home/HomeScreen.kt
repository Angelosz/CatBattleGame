package angelosz.catbattlegame.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import angelosz.catbattlegame.R


@Composable
fun HomeScreen(
    windowSize: WindowWidthSizeClass,
    onPlayButtonClick: () -> Unit,
    navigateToEncyclopedia: () -> Unit,
    navigateToCollection: () -> Unit,
    navigateToBattleChests:() -> Unit
) {
    val isPortraitView = windowSize != WindowWidthSizeClass.Expanded
    val background = if(isPortraitView) R.drawable.homescreen_portrait else R.drawable.homescreen_landscape

    Box(modifier = Modifier
        .fillMaxSize(),
    ){
        Image(
            painter = painterResource(background),
            contentDescription = "",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
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
                .align(Alignment.TopEnd)
                .padding(top = 64.dp, end = 8.dp),
        ) {
            Image(
                painter = painterResource(R.drawable.circular_button_128),
                contentDescription = null,
                modifier = Modifier
                    .size(96.dp)
            )
            Image(
                painter = painterResource(R.drawable.battlechest_256),
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .clickable(onClick = navigateToBattleChests )
            )
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