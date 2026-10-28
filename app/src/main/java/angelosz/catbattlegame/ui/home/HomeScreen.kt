package angelosz.catbattlegame.ui.home

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import angelosz.catbattlegame.BuildConfig
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


import angelosz.catbattlegame.ui.home.dialogs.DailyRewardDialog
import angelosz.catbattlegame.ui.home.dialogs.MailBoxDialog

@Composable
fun HomeScreen(
    windowSize: WindowWidthSizeClass,
    onPlayButtonClick: () -> Unit,
    openHalloweenEvent: () -> Unit = {},
    navigateToArchive: () -> Unit,
    navigateToCollections: () -> Unit,
    viewModel:HomeScreenViewModel = viewModel(factory = CatViewModelProvider.Factory)
) {
    val isPortraitView = windowSize != WindowWidthSizeClass.Expanded

    val uiState by viewModel.uiState.collectAsState()

    val bgRes = getHomeScreenBackgroundRes(isPortraitView, isBlurry = false)
    val bgBlurryRes = getHomeScreenBackgroundRes(isPortraitView, isBlurry = true)

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (uiState.screenState) {
            ScreenState.SUCCESS -> {
                if(uiState.notifications.isNotEmpty()){
                    BackgroundImage(bgBlurryRes)
                    ManageNotification(viewModel, uiState)
                } else {
                    BackgroundImage(bgRes)
                    HomeScreenContent(
                        gold = uiState.gold,
                        crystals = uiState.crystals,
                        unreadMailCount = uiState.unreadMailCount,
                        isDailyRewardAvailable = uiState.isDailyRewardAvailable,
                        onPlayButtonClick = onPlayButtonClick,
                        openHalloweenEvent = openHalloweenEvent,
                        navigateToCollections = navigateToCollections,
                        navigateToArchive = navigateToArchive,
                        openShop = viewModel::openShop,
                        openSettings = viewModel::openSettings,
                        openMailbox = viewModel::openMailbox,
                        openDailyReward = viewModel::openDailyReward
                    )
                    if(uiState.shopIsOpen){
                        if (isPortraitView)
                            PortraitShopScreen(
                                availableGold = uiState.gold,
                                closeShop = viewModel::closeShop,
                                buyBattleChest = viewModel::battleChestWasBought
                            )
                        else
                            LandscapeShopScreen(
                                availableGold = uiState.gold,
                                closeShop = viewModel::closeShop,
                                buyBattleChest = viewModel::battleChestWasBought
                            )
                    } else {
                        if(uiState.settingsIsOpen){
                            SettingsScreen(
                                settings = uiState.playerSettings,
                                saveSettings = viewModel::saveSettings,
                            )
                        }
                    }
                }

                if (uiState.mailboxIsOpen) {
                    MailBoxDialog(
                        mails = uiState.mails,
                        onClaimReward = { viewModel.claimMailReward(it) },
                        onDeleteMail = { viewModel.deleteMail(it) },
                        onDismiss = { viewModel.closeMailbox() }
                    )
                }

                if (uiState.dailyRewardIsOpen) {
                    DailyRewardDialog(
                        streakDay = uiState.dailyRewardStreak,
                        isClaimableToday = uiState.isDailyRewardAvailable,
                        onClaimReward = { viewModel.claimDailyReward() },
                        onDismiss = { viewModel.closeDailyReward() }
                    )
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
    val notificationAnimationOffsetY = remember { Animatable(0f) }
    val coroutineScope = rememberCoroutineScope()
    val notification = uiState.notifications.first()

    Box(
        modifier = Modifier.offset { IntOffset(0, notificationAnimationOffsetY.value.toInt()) }
    ){
        notification.display(
            onAccept = {
                coroutineScope.launch {
                    try {
                        notificationAnimationOffsetY.animateTo(
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
                        notificationAnimationOffsetY.snapTo(-2000f)
                        notificationAnimationOffsetY.animateTo(
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

private fun getHomeScreenBackgroundRes(isPortrait: Boolean, isBlurry: Boolean): Int {
    return if (isPortrait) {
        if (isBlurry) R.drawable.homescreen_portrait_halloween_blurry else R.drawable.homescreen_portrait_halloween
    } else {
        if (isBlurry) R.drawable.homescreen_landscape_halloween_blurry else R.drawable.homescreen_landscape_halloween
    }
}

@Composable
private fun HomeScreenContent(
    gold: Int,
    crystals: Int,
    unreadMailCount: Int,
    isDailyRewardAvailable: Boolean,
    onPlayButtonClick: () -> Unit,
    openHalloweenEvent: () -> Unit,
    navigateToCollections: () -> Unit,
    navigateToArchive: () -> Unit,
    openShop: () -> Unit,
    openSettings: () -> Unit,
    openMailbox: () -> Unit,
    openDailyReward: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // Main Center: Game Logo, Play Button, and Halloween Event Button
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Image(
                painter = painterResource(R.drawable.logo_256),
                contentDescription = "Cat Battles Logo",
                modifier = Modifier
                    .size(220.dp)
                    .padding(bottom = 16.dp)
            )
            HomeButton(
                modifier = Modifier.padding(4.dp),
                image = R.drawable.button_play,
                contentDescription = stringResource(R.string.play_button_desc),
                onButtonClicked = onPlayButtonClick
            )
            Spacer(modifier = Modifier.height(12.dp))
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF2C1C13)),
                border = BorderStroke(2.dp, Color(0xFFFF9900)),
                onClick = openHalloweenEvent,
                modifier = Modifier.padding(4.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Image(
                        painter = painterResource(R.drawable.campaign_halloween_icon256),
                        contentDescription = null,
                        modifier = Modifier.size(32.dp)
                    )
                    Text(
                        text = stringResource(R.string.campaign_title_halloween),
                        color = Color(0xFFFF9900),
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }

        // Top Zone: Currency Bar on Left, Calendar & Mail on Right
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .systemBarsPadding()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            HomeTopBar(gold = gold, crystals = crystals)
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(contentAlignment = Alignment.TopEnd) {
                    RoundedImageButton(
                        onClick = openDailyReward,
                        innerImage = R.drawable.calendar_button_256,
                        innerImageSize = 48
                    )
                    if (isDailyRewardAvailable) {
                        Card(
                            shape = CircleShape,
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFD700)),
                            modifier = Modifier.size(16.dp)
                        ) {}
                    }
                }
                Box(contentAlignment = Alignment.TopEnd) {
                    RoundedImageButton(
                        onClick = openMailbox,
                        innerImage = R.drawable.mail_128,
                        innerImageSize = 48
                    )
                    if (unreadMailCount > 0) {
                        Card(
                            shape = CircleShape,
                            colors = CardDefaults.cardColors(containerColor = Color.Red),
                            modifier = Modifier.padding(2.dp)
                        ) {
                            Text(
                                text = "$unreadMailCount",
                                color = Color.White,
                                style = MaterialTheme.typography.labelSmall,
                                modifier = Modifier.padding(horizontal = 4.dp)
                            )
                        }
                    }
                }
            }
        }

        // Bottom Zone: Secondary Menu Bar (Armory, Encyclopedia, Shop, Settings)
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .systemBarsPadding()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RoundedImageButton(
                onClick = navigateToCollections,
                innerImage = R.drawable.collections_button_256,
                innerImageSize = 56
            )
            RoundedImageButton(
                onClick = navigateToArchive,
                innerImage = R.drawable.archive_button_256,
                innerImageSize = 56
            )
            RoundedImageButton(
                onClick = openShop,
                innerImage = R.drawable.battlechest_256,
                innerImageSize = 56
            )
            RoundedImageButton(
                onClick = openSettings,
                innerImage = R.drawable.settings_button_256,
                innerImageSize = 56
            )
        }

        Text(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(horizontal = 16.dp, vertical = 16.dp),
            text = BuildConfig.VERSION_NAME,
            color = Color.White,
            style = MaterialTheme.typography.headlineMedium.copy(
                shadow = Shadow(
                    color = Color.Black,
                    offset = Offset(3f, 3f),
                    blurRadius = 1f
                )
            )
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
                contentDescription = stringResource(R.string.gold_coins),
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
                contentDescription = stringResource(R.string.crystals),
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