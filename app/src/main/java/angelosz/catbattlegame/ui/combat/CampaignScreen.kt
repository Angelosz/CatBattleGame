package angelosz.catbattlegame.ui.combat

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import angelosz.catbattlegame.CatViewModelProvider
import angelosz.catbattlegame.R
import angelosz.catbattlegame.domain.enums.CampaignState
import angelosz.catbattlegame.domain.enums.ScreenState
import angelosz.catbattlegame.domain.models.entities.Campaign
import angelosz.catbattlegame.domain.models.entities.CampaignChapter
import angelosz.catbattlegame.ui.components.BackgroundImage
import angelosz.catbattlegame.ui.components.FailureCard
import angelosz.catbattlegame.ui.components.LoadingCard
import angelosz.catbattlegame.ui.components.RoundedButton

@Composable
fun CampaignScreen(
    windowSize: WindowWidthSizeClass,
    onBackButtonPressed: () -> Unit,
    viewModel: CampaignScreenViewModel = viewModel(factory = CatViewModelProvider.Factory)
    ){
    val uiState by viewModel.uiState.collectAsState()
    val isPortraitView = windowSize != WindowWidthSizeClass.Expanded

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        BackgroundImage(R.drawable.encyclopedia_landscape_blurry)
        when(uiState.screenState){
            ScreenState.SUCCESS -> {
                when(uiState.stage){
                    CampaignSelectionStage.SELECTING_CAMPAIGN -> {
                        CampaignSelectionCarousel(
                            campaigns = uiState.campaigns,
                            onCampaignClicked = { campaign -> viewModel.selectCampaign(campaign) }
                        )
                    }
                    CampaignSelectionStage.SELECTING_CHAPTER -> {
                        CampaignChapterSelectionGrid(
                            chapters = uiState.campaignChapters,
                            onChapterClicked = {  }
                        )
                    }
                    CampaignSelectionStage.CHAPTER_SELECTED -> {

                    }
                }

            }
            ScreenState.LOADING -> {
                LoadingCard()
            }
            ScreenState.FAILURE -> {
                FailureCard(
                    onBackPressed = onBackButtonPressed,
                    onReloadPressed = { viewModel.setupInitialData() }
                )
            }
            ScreenState.WORKING -> { }
        }
    }
}

@Composable
fun CampaignChapterSelectionGrid(
    chapters: List<CampaignChapter>,
    onChapterClicked: (CampaignChapter) -> Unit
) {
    if(chapters.isNotEmpty()){
        Column(
            modifier = Modifier
                .systemBarsPadding()
                .fillMaxSize(),
            ) {
            LazyVerticalGrid(
                modifier = Modifier
                    .padding(32.dp)
                    .fillMaxSize(),
                columns = GridCells.Fixed(3),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(chapters) { chapter ->
                    RoundedButton(
                        onClick = { onChapterClicked(chapter) },
                        innerImage = chapter.image,
                        outerImage = R.drawable.iconflash_256,
                        innerImageSize = 96,
                        outerImageSize = 128
                    )
                }
            }
        }
    }
}

@Composable
private fun CampaignSelectionCarousel(
    campaigns: List<Campaign>,
    onCampaignClicked: (Campaign) -> Unit
) {
    if (campaigns.isNotEmpty()) {
        var selectedImageIndex by remember { mutableIntStateOf(0) }
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (selectedImageIndex > 0) {
                RoundedButton(
                    outerImage = R.drawable.iconflash_256,
                    outerImageSize = 128,
                    innerImage = campaigns[selectedImageIndex - 1].image,
                    innerImageSize = 92,
                    onClick = { selectedImageIndex-- },
                )
            } else {
                Spacer(modifier = Modifier.size(128.dp))
            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(vertical = 48.dp),
            ) {
                RoundedButton(
                    outerImage = R.drawable.iconflash_256,
                    outerImageSize = 256,
                    innerImage = campaigns[selectedImageIndex].image,
                    innerImageSize = 196,
                    onClick = {
                        if (campaigns[selectedImageIndex].state != CampaignState.LOCKED) {
                            onCampaignClicked(campaigns[selectedImageIndex])
                        }
                    },
                )
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White

                    )
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(
                            horizontal = 16.dp,
                            vertical = 8.dp
                        )
                    ) {
                        if (campaigns[selectedImageIndex].state == CampaignState.LOCKED) {
                            Icon(
                                imageVector = Icons.Filled.Lock,
                                contentDescription = "Locked Campaign",
                                modifier = Modifier.padding(horizontal = 4.dp)
                            )
                        }
                        Text(
                            text = campaigns[selectedImageIndex].name,
                        )
                    }
                }
            }
            if (selectedImageIndex < campaigns.lastIndex) {
                RoundedButton(
                    outerImage = R.drawable.iconflash_256,
                    outerImageSize = 128,
                    innerImage = campaigns[selectedImageIndex + 1].image,
                    innerImageSize = 92,
                    onClick = { selectedImageIndex++ },
                )
            } else {
                Spacer(modifier = Modifier.size(128.dp))
            }
        }
    }
}