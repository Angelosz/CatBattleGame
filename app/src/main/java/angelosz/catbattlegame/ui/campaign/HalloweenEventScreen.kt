package angelosz.catbattlegame.ui.campaign

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import angelosz.catbattlegame.CatViewModelProvider
import angelosz.catbattlegame.R
import angelosz.catbattlegame.domain.enums.CampaignState
import angelosz.catbattlegame.domain.enums.ScreenState
import angelosz.catbattlegame.ui.components.BackButton
import angelosz.catbattlegame.ui.components.BackgroundImage
import angelosz.catbattlegame.ui.components.FailureCard
import angelosz.catbattlegame.ui.components.LoadingCard
import angelosz.catbattlegame.ui.components.RoundedImageButton

@Composable
fun HalloweenEventScreen(
    windowSize: WindowWidthSizeClass,
    onBackButtonPressed: () -> Unit,
    onChapterSelected: (Long) -> Unit,
    viewModel: HalloweenEventViewModel = viewModel(factory = CatViewModelProvider.Factory)
) {
    BackHandler(onBack = onBackButtonPressed)
    val uiState by viewModel.uiState.collectAsState()
    val isPortraitView = windowSize != WindowWidthSizeClass.Expanded

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        BackgroundImage(if (isPortraitView) R.drawable.homescreen_portrait_halloween_blurry else R.drawable.homescreen_landscape_halloween_blurry)

        when (uiState.screenState) {
            ScreenState.SUCCESS -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .systemBarsPadding(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(R.string.campaign_title_halloween),
                        style = MaterialTheme.typography.displaySmall,
                        color = Color(0xFFFF9900),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(if (isPortraitView) 2 else 4),
                        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.padding(horizontal = 24.dp)
                    ) {
                        items(uiState.chapters) { item ->
                            val chapter = item.chapter
                            val isUnlocked = chapter.state != CampaignState.LOCKED

                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier.padding(8.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    RoundedImageButton(
                                        onClick = {
                                            if (isUnlocked) onChapterSelected(chapter.id)
                                        },
                                        innerImage = chapter.image,
                                        outerImage = R.drawable.iconflash_256,
                                        innerImageSize = 96,
                                        outerImageSize = 128
                                    )
                                    if (!isUnlocked) {
                                        Card(
                                            modifier = Modifier
                                                .align(Alignment.BottomCenter)
                                                .shadow(2.dp, CircleShape),
                                            shape = CircleShape,
                                            colors = CardDefaults.cardColors(containerColor = Color.White)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Filled.Lock,
                                                contentDescription = stringResource(R.string.locked_chapter),
                                                modifier = Modifier.padding(4.dp)
                                            )
                                        }
                                    }
                                }

                                Text(
                                    text = stringResource(chapter.name),
                                    style = MaterialTheme.typography.titleSmall,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(top = 4.dp)
                                )

                                Card(
                                    modifier = Modifier.padding(top = 2.dp),
                                    shape = CircleShape,
                                    colors = CardDefaults.cardColors(containerColor = Color(0xFF2C1C13)),
                                    border = BorderStroke(1.dp, Color(0xFFFF9900))
                                ) {
                                    Text(
                                        text = "Lv. ${chapter.recommendedLevel}",
                                        color = Color(0xFFFF9900),
                                        fontWeight = FontWeight.Bold,
                                        style = MaterialTheme.typography.labelSmall,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                    )
                                }

                                if (isUnlocked) {
                                    Card(
                                        modifier = Modifier.padding(top = 4.dp),
                                        shape = CircleShape,
                                        colors = CardDefaults.cardColors(
                                            containerColor = if (item.isDailyClaimed) Color(0xFF1B5E20) else Color(0xFFE65100)
                                        )
                                    ) {
                                        Text(
                                            text = if (item.isDailyClaimed) "Reclamado Hoy" else "Diaria Disponible",
                                            color = Color.White,
                                            style = MaterialTheme.typography.labelSmall,
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                BackButton(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(horizontal = 32.dp, vertical = 48.dp),
                    onBackPressed = onBackButtonPressed
                )
            }

            ScreenState.LOADING -> LoadingCard()
            ScreenState.FAILURE -> FailureCard(onBackPressed = onBackButtonPressed, onReloadPressed = viewModel::setup)
            ScreenState.INITIALIZING -> {
                LoadingCard()
                LaunchedEffect(viewModel) {
                    viewModel.setup()
                }
            }
        }
    }
}
