package angelosz.catbattlegame.ui.combat.combatreward

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import angelosz.catbattlegame.CatViewModelProvider
import angelosz.catbattlegame.R
import angelosz.catbattlegame.domain.enums.CombatResult
import angelosz.catbattlegame.domain.enums.RewardType
import angelosz.catbattlegame.domain.enums.ScreenState
import angelosz.catbattlegame.ui.components.BackgroundImage
import angelosz.catbattlegame.ui.components.FailureCard
import angelosz.catbattlegame.ui.components.LoadingCard

@Composable
fun HalloweenCombatResultScreen(
    teamId: Long,
    chapterId: Long,
    combatResult: CombatResult,
    viewModel: CombatResultViewModel = viewModel(factory = CatViewModelProvider.Factory),
    onBackPressed: () -> Unit,
    onReturnToHomePressed: () -> Unit
) {
    BackHandler(onBack = onReturnToHomePressed)
    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        BackgroundImage(R.drawable.homescreen_landscape_halloween_blurry)

        when (uiState.screenState) {
            ScreenState.SUCCESS -> {
                Card(
                    modifier = Modifier
                        .padding(16.dp)
                        .width(384.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF2C1C13))
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = when (uiState.combatResult) {
                                CombatResult.PLAYER_WON -> stringResource(R.string.victory)
                                CombatResult.PLAYER_LOST -> stringResource(R.string.defeat)
                                CombatResult.TIED -> stringResource(R.string.tie)
                            },
                            style = MaterialTheme.typography.displaySmall,
                            color = Color(0xFFFF9900),
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(8.dp)
                        )

                        Card(
                            modifier = Modifier.padding(8.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White)
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Text(
                                    text = stringResource(R.string.loot),
                                    style = MaterialTheme.typography.labelLarge,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(4.dp)
                                )

                                LazyVerticalGrid(
                                    columns = GridCells.FixedSize(96.dp),
                                    horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterHorizontally),
                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    items(uiState.chapterReward) { chapterReward ->
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Image(
                                                painter = painterResource(
                                                    when (chapterReward.rewardType) {
                                                        RewardType.GOLD -> R.drawable.goldcoins_128
                                                        RewardType.CRYSTAL -> R.drawable.crystals_128
                                                        else -> R.drawable.battlechest_halloween_96
                                                    }
                                                ),
                                                contentDescription = null,
                                                modifier = Modifier.size(64.dp)
                                            )
                                            Text(
                                                text = chapterReward.amount.toString(),
                                                style = MaterialTheme.typography.labelLarge,
                                                modifier = Modifier.padding(horizontal = 4.dp)
                                            )
                                        }
                                    }
                                }

                                Text(
                                    text = stringResource(
                                        R.string.experience_desc,
                                        uiState.experienceGained
                                    ),
                                    style = MaterialTheme.typography.labelLarge,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(vertical = 8.dp)
                                )

                                Button(
                                    modifier = Modifier.padding(top = 8.dp),
                                    onClick = onReturnToHomePressed,
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(0xFFE65100)
                                    )
                                ) {
                                    Text(
                                        text = stringResource(R.string.home),
                                        color = Color.White,
                                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            ScreenState.LOADING -> LoadingCard()
            ScreenState.FAILURE -> FailureCard(onBackPressed = onBackPressed, onReloadPressed = {})
            ScreenState.INITIALIZING -> {
                LaunchedEffect(chapterId, teamId) {
                    viewModel.setupCombatResult(teamId, chapterId, combatResult)
                }
            }
        }
    }
}
