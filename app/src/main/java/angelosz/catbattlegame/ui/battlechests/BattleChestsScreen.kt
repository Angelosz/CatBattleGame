package angelosz.catbattlegame.ui.battlechests

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import angelosz.catbattlegame.CatViewModelProvider
import angelosz.catbattlegame.R
import angelosz.catbattlegame.data.entities.BattleChest
import angelosz.catbattlegame.domain.enums.BattleChestType
import angelosz.catbattlegame.domain.enums.CatRarity
import angelosz.catbattlegame.domain.enums.ScreenState
import angelosz.catbattlegame.ui.components.BackButton
import angelosz.catbattlegame.ui.components.BackgroundImage
import angelosz.catbattlegame.ui.components.CatCard
import angelosz.catbattlegame.ui.components.FailureCard
import angelosz.catbattlegame.ui.components.LoadingCard
import angelosz.catbattlegame.ui.theme.CatBattleGameTheme
import kotlinx.coroutines.launch

@Composable
fun BattleChestsScreen(
    onBackPressed: () -> Unit,
    navigateToCollection: () -> Unit
) {
    val viewModel: BattleChestsViewModel = viewModel(factory = CatViewModelProvider.Factory)
    val uiState by viewModel.uiState.collectAsState()

    val lootBoxAnimationOffsetY = remember { Animatable(0f) }
    val coroutineScope = rememberCoroutineScope()

    BackHandler( onBack = onBackPressed )

    when(uiState.screenState){
        ScreenState.SUCCESS -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                BackgroundImage(R.drawable.encyclopedia_landscape_blurry)
                if(uiState.catReward == null){
                    /* Battle Chest */
                    val battleChest = uiState.selectedBattleChest
                    if(battleChest != null){
                        Column(
                            modifier = Modifier.offset { IntOffset(0, lootBoxAnimationOffsetY.value.toInt()) },
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ){
                            ShowBattleChest(
                                battleChest = battleChest,
                                onBattleChestClicked = {
                                    coroutineScope.launch {
                                        try {
                                            lootBoxAnimationOffsetY.animateTo(
                                                2000f,
                                                animationSpec = tween(1000)
                                            )
                                            viewModel.openSelectedBattleChest()
                                        } catch (e: Exception) {
                                            Log.d("battleChest", e.message.toString())
                                        }
                                    }
                                }
                            )
                            TextCard(uiState.message)
                        }
                    } else {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 32.dp, vertical = 64.dp),
                            contentAlignment = Alignment.Center
                        ){
                            if(uiState.battleChests.isNotEmpty()){
                                ShowBattleChestList(
                                    battleChests = uiState.battleChests,
                                    onChestClicked = { clickedChest ->
                                        viewModel.selectBattleChest(clickedChest)
                                    }
                                )
                            } else {
                                Card(){
                                    Text(
                                        modifier = Modifier.padding(8.dp),
                                        text = "You have no packages left! :("
                                    )
                                }
                            }
                            BackButton(
                                modifier = Modifier.align(Alignment.BottomEnd),
                                onBackPressed = onBackPressed
                            )
                        }
                    }
                } else {
                    val cardScale = animateFloatAsState(targetValue =  1f, animationSpec = tween(2000))
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .scale(cardScale.value),
                        contentAlignment = Alignment.Center
                    ){
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.SpaceEvenly
                        ){
                            uiState.catReward?.let { cat ->
                                CatCard(
                                    id = cat.id,
                                    image = cat.image,
                                    imageSize = 256,
                                    onCardClicked = {},
                                    showBorder = true,
                                )
                                Card(
                                    modifier = Modifier.padding(8.dp),
                                    colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface)
                                ){
                                    Text(
                                        text = uiState.message,
                                        modifier = Modifier.padding(16.dp),
                                        textAlign = TextAlign.Center,
                                        style = MaterialTheme.typography.labelLarge
                                    )
                                }
                                Spacer(modifier = Modifier.height(16.dp))
                                Row {
                                    Button(
                                        onClick = {
                                            viewModel.goBackToBattleChestsGrid()
                                            coroutineScope.launch {
                                                lootBoxAnimationOffsetY.snapTo(0f)
                                            }
                                        }
                                    ) {
                                        Text(
                                            text = "Open more Packages!",
                                            modifier = Modifier.padding(8.dp),
                                            textAlign = TextAlign.Center,
                                            style = MaterialTheme.typography.labelLarge
                                        )
                                    }
                                    Button(
                                        onClick = navigateToCollection,
                                    ) {
                                        Text(
                                            text = "Go to Collection.",
                                            modifier = Modifier.padding(8.dp),
                                            textAlign = TextAlign.Center,
                                            style = MaterialTheme.typography.labelLarge
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        ScreenState.LOADING -> {
            LoadingCard()
        }
        ScreenState.FAILURE -> {
            FailureCard(
                onBackPressed = onBackPressed,
                onReloadPressed = { viewModel.setupInitialData() }
            )
        }
        ScreenState.INITIALIZING -> { }
    }
}


@Composable
private fun ShowBattleChestList(
    battleChests: Map<Pair<CatRarity, BattleChestType>, List<BattleChest>>,
    onChestClicked: (BattleChest) -> Unit
) {

    val boxSize = 96
    val battleChestsLists = battleChests.values.toList()

    LazyVerticalGrid(
        columns = GridCells.FixedSize(boxSize.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(battleChestsLists) { battleChestList ->
            BattleChestSlot(
                boxSize = boxSize,
                onChestClicked = onChestClicked,
                battleChest = battleChestList.first(),
                amount = battleChestList.size
            )
        }
    }
}


@Composable
private fun BattleChestSlot(
    boxSize: Int = 96,
    onChestClicked: (BattleChest) -> Unit,
    battleChest: BattleChest,
    amount: Int,
) {
    Column{
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(boxSize.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.battlechest_background_128),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Fit
            )
            Image(
                painter = painterResource(R.drawable.battlechest_256),
                contentDescription = null,
                modifier = Modifier
                    .size((boxSize * 0.8f).dp)
                    .clickable(onClick = { onChestClicked(battleChest) })
            )
            Card(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .size(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Red),
                elevation = CardDefaults.cardElevation(2.dp),
                shape = CircleShape,
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ){
                    Text(
                        text = "$amount",
                        color = Color.White,
                        style = MaterialTheme.typography.labelLarge,
                    )
                }
            }
        }
        Card(
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            shape = RectangleShape,
            modifier = Modifier.width(boxSize.dp)
        ) {
            Text(
                text = "${stringResource(battleChest.type.res)}\n${stringResource(battleChest.rarity.res)}\nPackage",
                style = MaterialTheme.typography.labelMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun TextCard(message: String) {
    Card(
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primary)
    ) {
        Text(
            text = message,
            modifier = Modifier.padding(8.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.labelMedium
        )
    }
}

@Composable
private fun ShowBattleChest(
    battleChest: BattleChest,
    onBattleChestClicked: () -> Unit,
) {
    Card(
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.tertiaryContainer)
    ) {
        Text(
            text = stringResource(
                R.string.package_description,
                battleChest.rarity,
                stringResource(battleChest.type.res)
            ),
            modifier = Modifier.padding(8.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.labelLarge
        )
    }

    Image(
        painter = painterResource(R.drawable.battlechest_512),
        contentDescription = null,
        modifier = Modifier
            .padding(32.dp)
            .clickable(onClick = onBattleChestClicked)
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BattleChestListPreview(){
    CatBattleGameTheme {
        ShowBattleChestList(
            mapOf(
                Pair(CatRarity.KITTEN, BattleChestType.NORMAL) to listOf(BattleChest()),
                Pair(CatRarity.KITTEN, BattleChestType.NEW_CAT) to listOf(BattleChest()),
            ),
            onChestClicked = {},
        )
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ChestSlotPreview(){
    CatBattleGameTheme {
        BattleChestSlot(
            onChestClicked = {},
            battleChest = BattleChest(),
            amount = 1,
            boxSize = 96
        )
    }
}