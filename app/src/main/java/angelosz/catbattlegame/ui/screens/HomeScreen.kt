package angelosz.catbattlegame.ui.screens

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import angelosz.catbattlegame.R


@Composable
fun HomeScreen(
    windowSize: WindowWidthSizeClass,
    navigateToEncyclopedia: () -> Unit
) {
    if(windowSize == WindowWidthSizeClass.Expanded){
        LandscapeHomeScreen(navigateToEncyclopedia,)
    } else {
        PortraitHomeScreen(navigateToEncyclopedia)
    }
}

@Composable
private fun LandscapeHomeScreen(
    navigateToEncyclopedia: () -> Unit,
    ) {
    Box(modifier = Modifier
        .fillMaxSize(),
    ){
        Image(
            painter = painterResource(R.drawable.homescreen_landscape),
            contentDescription = "",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Image(
                painter = painterResource(R.mipmap.ic_launcher_foreground),
                contentDescription = "",
                modifier = Modifier.size(192.dp)
            )
            HomeButton(
                modifier = Modifier.padding(8.dp),
                image = R.drawable.button_play,
                contentDescription = "Play button",
                onButtonClicked = {}
            )
            HomeButton(
                modifier = Modifier.padding(8.dp),
                image = R.drawable.button_collection,
                contentDescription = "Collection button",
                onButtonClicked = {}
            )
            HomeButton(
                modifier = Modifier.padding(8.dp),
                image = R.drawable.button_encyclopedia,
                contentDescription = "Encyclopedia button",
                onButtonClicked = navigateToEncyclopedia
            )
        }
    }
}

@Composable
private fun PortraitHomeScreen(navigateToEncyclopedia: () -> Unit) {
    Box(modifier = Modifier
        .fillMaxSize(),
    ){
        Image(
            painter = painterResource(R.drawable.homescreen_portrait),
            contentDescription = "",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Spacer(modifier = Modifier.fillMaxSize().weight(0.2f))

            Image(
                painter = painterResource(R.mipmap.ic_launcher_foreground),
                contentDescription = "",
                modifier = Modifier.fillMaxSize().weight(0.2f),
            )

            Spacer(modifier = Modifier.fillMaxSize().weight(0.2f))

            HomeButton(
                modifier = Modifier.weight(0.1f),
                image = R.drawable.button_play,
                contentDescription = "Play button",
                onButtonClicked = {}
            )
            HomeButton(
                modifier = Modifier.weight(0.1f),
                image = R.drawable.button_collection,
                contentDescription = "Collection button",
                onButtonClicked = {}
            )
            HomeButton(
                modifier = Modifier.weight(0.1f),
                image = R.drawable.button_encyclopedia,
                contentDescription = "Encyclopedia button",
                onButtonClicked = navigateToEncyclopedia
            )
            Spacer(modifier = Modifier.fillMaxSize().weight(0.1f))
        }
    }
}

@Composable
private fun HomeButton(
    modifier: Modifier = Modifier,
    @DrawableRes image: Int,
    contentDescription: String,
    onButtonClicked: () -> Unit
) {
    Button(
        onClick = onButtonClicked,
        modifier = modifier
            .width(192.dp)
            .height(48.dp),
        contentPadding = PaddingValues(0.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = Color.Transparent
        )
    ) {
        Image(
            painter = painterResource(image),
            contentDescription = contentDescription,
            modifier = Modifier.fillMaxSize()
        )
    }
}