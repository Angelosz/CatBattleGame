package angelosz.catbattlegame.ui.home

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import angelosz.catbattlegame.CatViewModelProvider
import angelosz.catbattlegame.R
import angelosz.catbattlegame.domain.enums.ScreenState
import angelosz.catbattlegame.ui.components.BackgroundImage
import angelosz.catbattlegame.ui.components.FailureCard
import angelosz.catbattlegame.ui.components.LoadingCard
import angelosz.catbattlegame.ui.components.RoundedImageButton
import angelosz.catbattlegame.ui.home.notifications.BattleChestNotification
import angelosz.catbattlegame.ui.home.notifications.CatEvolutionNotification
import angelosz.catbattlegame.ui.home.notifications.CurrencyRewardNotification
import angelosz.catbattlegame.ui.home.notifications.LevelUpNotification
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


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
        contentAlignment = Alignment.Center
    ) {
        when (uiState.screenState) {
            ScreenState.SUCCESS -> {
                if(uiState.notifications.isNotEmpty()){
                    BackgroundImage(if (isPortraitView) R.drawable.homescreen_portrait_blurry else R.drawable.homescreen_landscape_blurry)
                    ManageNotification(viewModel, uiState)
                } else {
                    BackgroundImage(if (isPortraitView) R.drawable.homescreen_portrait else R.drawable.homescreen_landscape)
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
fun ManageNotification(viewModel: HomeScreenViewModel, uiState: HomeScreenUiState) {
    val lootBoxAnimationOffsetY = remember { Animatable(0f) }
    val coroutineScope = rememberCoroutineScope()
    val notification = uiState.notifications.first()
    Box(
        modifier = Modifier.offset { IntOffset(0, lootBoxAnimationOffsetY.value.toInt()) }
    ){
        notification.display(
            onAccept = {
                coroutineScope.launch {
                    try {
                        lootBoxAnimationOffsetY.animateTo(
                            2000f,
                            animationSpec = tween(500)
                        )
                        when(notification){
                            is LevelUpNotification -> {}
                            is CatEvolutionNotification -> {}
                            is BattleChestNotification -> { viewModel.addBattleChestToAccount(notification.rarity, notification.battleChestType) }
                            is CurrencyRewardNotification -> { viewModel.addCurrencyToAccount(notification.amount, notification.currencyType) }
                        }
                        viewModel.closeNotification()
                        delay(100)
                        lootBoxAnimationOffsetY.snapTo(-2000f)
                        lootBoxAnimationOffsetY.animateTo(
                            0f,
                            animationSpec = tween(500)
                        )
                    } catch (e: Exception) {
                        Log.d("CatBattle: BattleChest", e.message.toString())
                    }
                }

            }
        )()
    }
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