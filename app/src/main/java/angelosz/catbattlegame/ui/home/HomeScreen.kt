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
import androidx.compose.runtime.LaunchedEffect
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
import angelosz.catbattlegame.domain.enums.ScreenState
import angelosz.catbattlegame.ui.components.BackgroundImage
import angelosz.catbattlegame.ui.components.FailureCard
import angelosz.catbattlegame.ui.components.LoadingCard
import angelosz.catbattlegame.ui.components.RoundedImageButton
import angelosz.catbattlegame.ui.home.notifications.HomeNotification


@Composable
fun HomeScreen(
    windowSize: WindowWidthSizeClass,
    onPlayButtonClick: () -> Unit,
    navigateToArchive: () -> Unit,
    navigateToCollections: () -> Unit,
    viewModel:HomeScreenViewModel = viewModel(factory = CatViewModelProvider.Factory)
) {
    val isPortraitView = windowSize != WindowWidthSizeClass.Expanded

    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        BackgroundImage(if (isPortraitView) R.drawable.homescreen_portrait else R.drawable.homescreen_landscape)
        when (uiState.screenState) {
            ScreenState.SUCCESS -> {
                if(uiState.notifications.isNotEmpty()){
                    ManageNotifications(uiState.notifications, onNextClicked = {})
                } else {
                    HomeScreenContent(onPlayButtonClick, uiState, navigateToCollections, navigateToArchive)
                }
            }

            ScreenState.LOADING -> LoadingCard()
            ScreenState.FAILURE -> FailureCard({})
            ScreenState.INITIALIZING -> {
                LoadingCard()
                LaunchedEffect(viewModel) {
                    viewModel.setup()
                }
            }
        }
    }
}

@Composable
fun ManageNotifications(notifications: List<HomeNotification>, onNextClicked: () -> Unit) {
    TODO("Not yet implemented")
}

@Composable
private fun HomeScreenContent(
    onPlayButtonClick: () -> Unit,
    uiState: HomeScreenUiState,
    navigateToCollections: () -> Unit,
    navigateToArchive: () -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Image(
                painter = painterResource(R.mipmap.ic_launcher_foreground),
                contentDescription = "",
                modifier = Modifier
                    .size(256.dp)
                    .padding(bottom = 64.dp)
            )
            HomeButton(
                modifier = Modifier.padding(8.dp),
                image = R.drawable.button_play,
                contentDescription = "Play button",
                onButtonClicked = onPlayButtonClick
            )
        }

        HomeTopBar(
            modifier = Modifier.Companion
                .align(Alignment.TopStart)
                .systemBarsPadding(),
            gold = uiState.gold,
            crystals = uiState.crystals
        )
        HomeSideButtons(
            modifier = Modifier.Companion
                .align(Alignment.CenterEnd)
                .padding(16.dp),
            navigateToCollections = navigateToCollections,
            navigateToArchive = navigateToArchive
        )
    }
}

@Composable
fun HomeTopBar(
    modifier: Modifier = Modifier,
    gold: Int,
    crystals: Int
){
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(start = 16.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.goldcoins_128),
                contentDescription = "Gold coins",
                modifier = Modifier.size(32.dp)
            )
            Text(
                text = "$gold",
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
                text = "$crystals",
                style = MaterialTheme.typography.labelLarge,
                color = Color.White
            )
        }
    }
}

@Composable
fun HomeSideButtons(
    modifier: Modifier,
    navigateToCollections: () -> Unit,
    navigateToArchive: () -> Unit,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Column() {
            RoundedImageButton(
                onClick = navigateToCollections,
                innerImage = R.drawable.collections_button_256,
            )
            RoundedImageButton(
                onClick = navigateToArchive,
                innerImage = R.drawable.archive_button_256,
            )
        }
    }
}