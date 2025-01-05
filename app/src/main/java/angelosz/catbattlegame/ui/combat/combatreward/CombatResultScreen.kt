package angelosz.catbattlegame.ui.combat.combatreward

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import angelosz.catbattlegame.CatViewModelProvider
import angelosz.catbattlegame.R
import angelosz.catbattlegame.domain.enums.CombatResult
import angelosz.catbattlegame.domain.enums.RewardType
import angelosz.catbattlegame.domain.enums.ScreenState
import angelosz.catbattlegame.ui.armory.enums.CollectionsView
import angelosz.catbattlegame.ui.components.BackgroundImage
import angelosz.catbattlegame.ui.components.FailureCard
import angelosz.catbattlegame.ui.components.LoadingCard

@Composable
fun CombatResultScreen(
    teamId: Long,
    chapterId: Long,
    combatResult: CombatResult,
    viewModel: CombatResultViewModel = viewModel(factory = CatViewModelProvider.Factory),
    onBackPressed: () -> Unit,
    onReturnToHomePressed: () -> Unit,
    goToArmory: (CollectionsView) -> Unit = {},
) {
    BackHandler(onBack = {})
    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        BackgroundImage(R.drawable.player_collection_landscape_blurred)

        when(uiState.screenState){
            ScreenState.SUCCESS -> {
                Card(
                    modifier = Modifier
                        .padding(16.dp)
                ){
                    Column(
                        modifier = Modifier
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth(),
                            text = when(uiState.combatResult) {
                                CombatResult.PLAYER_WON -> "Victory!"
                                CombatResult.PLAYER_LOST -> "Defeat :("
                                CombatResult.TIED -> "You tied? How?!"
                            },
                            style = MaterialTheme.typography.displaySmall,
                            textAlign = TextAlign.Center
                        )
                        Card(
                            modifier = Modifier.padding(8.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White
                            )
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    modifier = Modifier
                                        .padding(4.dp)
                                        .fillMaxWidth(),
                                    text = "Loot:",
                                    style = MaterialTheme.typography.labelLarge,
                                    textAlign = TextAlign.Center
                                )
                                LazyVerticalGrid(
                                    columns = GridCells.FixedSize(96.dp),
                                    horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterHorizontally),
                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    items(uiState.chapterReward){ chapterReward ->
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically
                                        ){
                                            Image(
                                                painter = painterResource(
                                                    when(chapterReward.rewardType){
                                                        RewardType.GOLD -> R.drawable.goldcoins_128
                                                        RewardType.CRYSTAL -> R.drawable.crystals_128
                                                        else -> R.drawable.battlechest_96
                                                    }
                                                ),
                                                contentDescription = null,
                                                modifier = Modifier.size(64.dp)
                                            )
                                            Text(
                                                modifier = Modifier.padding(horizontal = 4.dp),
                                                text = chapterReward.amount.toString(),
                                                style = MaterialTheme.typography.labelLarge
                                            )
                                        }
                                    }
                                }
                                Text(
                                    modifier = Modifier
                                        .padding(vertical = 8.dp)
                                        .fillMaxWidth(),
                                    text = "Experience: ${uiState.experienceGained}",
                                    style = MaterialTheme.typography.labelLarge,
                                    textAlign = TextAlign.Center
                                )
                                Row {
                                    Button(
                                        modifier = Modifier.padding(4.dp),
                                        onClick = onReturnToHomePressed
                                    ) {
                                        Text(
                                            modifier = Modifier.padding(4.dp),
                                            text = "Home"
                                        )
                                    }
                                    Button(
                                        modifier = Modifier.padding(4.dp),
                                        onClick = { goToArmory(CollectionsView.CATS) }
                                    ) {
                                        Text(
                                            modifier = Modifier.padding(4.dp),
                                            text = "Armory"
                                        )
                                    }
                                }
                                if(viewModel.rewardIncludesBattleChest()){
                                    Button(
                                        modifier = Modifier.padding(4.dp),
                                        onClick = { goToArmory(CollectionsView.BATTLE_CHESTS) }
                                    ) {
                                        Text(
                                            modifier = Modifier.padding(4.dp),
                                            text = "Packages"
                                        )
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
                    onReloadPressed = {}
                )
            }
            ScreenState.INITIALIZING -> {
                LaunchedEffect(teamId, chapterId) {
                    viewModel.setupCombatResult(teamId, chapterId, combatResult)
                }
            }
        }
    }
}