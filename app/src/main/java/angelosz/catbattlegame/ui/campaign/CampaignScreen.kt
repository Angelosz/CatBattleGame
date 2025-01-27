package angelosz.catbattlegame.ui.campaign

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import angelosz.catbattlegame.CatViewModelProvider
import angelosz.catbattlegame.R
import angelosz.catbattlegame.domain.enums.CampaignState
import angelosz.catbattlegame.domain.enums.ScreenState
import angelosz.catbattlegame.ui.campaign.data.Campaign
import angelosz.catbattlegame.ui.campaign.data.Chapter
import angelosz.catbattlegame.ui.components.BackButton
import angelosz.catbattlegame.ui.components.BackgroundImage
import angelosz.catbattlegame.ui.components.CatCard
import angelosz.catbattlegame.ui.components.FailureCard
import angelosz.catbattlegame.ui.components.LoadingCard
import angelosz.catbattlegame.ui.components.RoundedImageButton

@Composable
fun CampaignScreen(
    windowSize: WindowWidthSizeClass,
    onBackButtonPressed: () -> Unit,
    onChapterSelected: (Long) -> Unit,
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
                        BackHandler(onBack = onBackButtonPressed)
                        BackButton(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(horizontal = 32.dp, vertical = 56.dp),
                            onBackPressed = onBackButtonPressed
                        )
                        if(isPortraitView){
                            PortraitCampaignSelectionCarousel(
                                campaigns = uiState.campaigns,
                                onCampaignClicked = { campaign -> viewModel.selectCampaign(campaign) },
                                selectedCampaignIndex = uiState.selectedCampaign.id.toInt() - 1
                            )
                        } else {
                            LandscapeCampaignSelectionCarousel(
                                campaigns = uiState.campaigns,
                                onCampaignClicked = { campaign -> viewModel.selectCampaign(campaign) },
                                selectedCampaignIndex = uiState.selectedCampaign.id.toInt() - 1
                            )
                        }
                    }
                    CampaignSelectionStage.SELECTING_CHAPTER -> {
                        BackHandler(onBack = { viewModel.backToCampaignSelection() })
                        CampaignChapterSelectionGrid(
                            chapters = uiState.campaignChapters,
                            chapterPerRow = if(isPortraitView) 3 else 6,
                            onChapterClicked = { chapter ->
                                if(chapter.state != CampaignState.LOCKED) {
                                    //viewModel.selectCampaignChapter(chapter)
                                    onChapterSelected(chapter.id)
                                }
                            }
                        )
                        BackButton(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(horizontal = 32.dp, vertical = 56.dp),
                            onBackPressed = { viewModel.backToCampaignSelection() }
                        )
                    }
                    CampaignSelectionStage.CHAPTER_SELECTED -> {
                        /*BackHandler(onBack = { viewModel.backToCampaignChapterSelection() })
                        BackButton(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(horizontal = 32.dp, vertical = 56.dp),
                            onBackPressed = { viewModel.backToCampaignChapterSelection() }
                        )
                        ChapterInfoCard(uiState, onChooseTeamClicked = onChapterSelected)*/
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
            ScreenState.INITIALIZING -> {
                LoadingCard()
                LaunchedEffect(viewModel) {
                    viewModel.setupInitialData()
                }
            }
        }
    }
}

@Composable
private fun ChapterInfoCard(
    uiState: CampaignScreenUiState,
    onChooseTeamClicked: (Long) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        RoundedImageButton(
            outerImage = R.drawable.iconflash_256,
            outerImageSize = 256,
            innerImage = uiState.selectedCampaignChapter.image,
            innerImageSize = 196,
            onClick = { },
        )
        Card(
            modifier = Modifier.padding(16.dp),
        ) {
            Column(
                modifier = Modifier.padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = uiState.selectedCampaignChapter.name,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    text = uiState.selectedCampaignChapter.description,
                    modifier = Modifier.padding(horizontal = 32.dp, vertical = 8.dp),
                    style = MaterialTheme.typography.bodyLarge
                )
                Card(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                ) {
                    LazyVerticalGrid(
                        contentPadding = PaddingValues(16.dp),
                        columns = GridCells.Fixed(2),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.height(224.dp)
                    ) {
                        items(uiState.selectedCampaignChapterEnemyCats) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {
                                CatCard(
                                    image = it.image,
                                    imageSize = 128
                                )
                                Card(
                                    modifier = Modifier.padding(top = 4.dp),
                                ) {
                                    Text(
                                        text = it.name,
                                        modifier = Modifier.padding(8.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.BottomCenter
            ){
                Button(
                    modifier = Modifier.padding(4.dp),
                    onClick = { onChooseTeamClicked(uiState.selectedCampaignChapter.id) }
                ){
                    Text(
                        modifier = Modifier.padding(2.dp),
                        text = stringResource(R.string.start),
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            }
        }
    }
}

@Composable
fun CampaignChapterSelectionGrid(
    chapters: List<Chapter>,
    chapterPerRow: Int,
    onChapterClicked: (Chapter) -> Unit
) {
    if(chapters.isNotEmpty()){
        Column(
            modifier = Modifier
                .systemBarsPadding()
                .height(640.dp),
            ) {
            LazyVerticalGrid(
                modifier = Modifier
                    .padding(32.dp),
                columns = GridCells.Fixed(chapterPerRow),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(chapters) { chapter ->
                    Box(contentAlignment = Alignment.Center) {
                        RoundedImageButton(
                            onClick = { onChapterClicked(chapter) },
                            innerImage = chapter.image,
                            outerImage = R.drawable.iconflash_256,
                            innerImageSize = 96,
                            outerImageSize = 128
                        )
                        if(chapter.state == CampaignState.LOCKED){
                            Card(
                                modifier = Modifier
                                    .align(Alignment.BottomCenter)
                                    .shadow(2.dp, CircleShape),
                                shape = CircleShape,
                                colors = CardDefaults.cardColors(
                                    containerColor = Color.White
                                ),
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Lock,
                                    contentDescription = stringResource(R.string.locked_chapter),
                                    modifier = Modifier.padding(4.dp),
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun PortraitCampaignSelectionCarousel(
    campaigns: List<Campaign>,
    onCampaignClicked: (Campaign) -> Unit,
    selectedCampaignIndex: Int = 0
) {
    if (campaigns.isNotEmpty()) {
        var selectedImageIndex by remember { mutableIntStateOf(if(selectedCampaignIndex >= 0) selectedCampaignIndex else 0) }
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (selectedImageIndex > 0) {
                RoundedImageButton(
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
                RoundedImageButton(
                    outerImage = R.drawable.iconflash_256,
                    outerImageSize = 256,
                    innerImage = campaigns[selectedImageIndex].image,
                    innerImageSize = 196,
                    onClick = {
                        if (campaigns[selectedImageIndex].state != CampaignState.LOCKED
                            && campaigns[selectedImageIndex].state != CampaignState.DEVELOPMENT) {
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
                        if (campaigns[selectedImageIndex].state == CampaignState.LOCKED
                            || campaigns[selectedImageIndex].state == CampaignState.DEVELOPMENT) {
                            Icon(
                                imageVector = Icons.Filled.Lock,
                                contentDescription = stringResource(R.string.locked_campaign),
                                modifier = Modifier.padding(horizontal = 4.dp)
                            )
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = campaigns[selectedImageIndex].name,
                                )
                                if(campaigns[selectedImageIndex].state == CampaignState.DEVELOPMENT){
                                    Text(
                                        text = stringResource(R.string.in_development),
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        } else {
                            Text(
                                text = campaigns[selectedImageIndex].name,
                            )
                        }
                    }
                }
            }
            if (selectedImageIndex < campaigns.lastIndex) {
                RoundedImageButton(
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


@Composable
private fun LandscapeCampaignSelectionCarousel(
    campaigns: List<Campaign>,
    onCampaignClicked: (Campaign) -> Unit,
    selectedCampaignIndex: Int = 0
) {
    if (campaigns.isNotEmpty()) {
        var selectedImageIndex by remember { mutableIntStateOf(if(selectedCampaignIndex >= 0) selectedCampaignIndex else 0) }
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (selectedImageIndex > 0) {
                RoundedImageButton(
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
                RoundedImageButton(
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
                        if (campaigns[selectedImageIndex].state == CampaignState.LOCKED
                            || campaigns[selectedImageIndex].state == CampaignState.DEVELOPMENT) {
                            Icon(
                                imageVector = Icons.Filled.Lock,
                                contentDescription = stringResource(R.string.locked_campaign),
                                modifier = Modifier.padding(horizontal = 4.dp)
                            )
                            Text(
                                text = campaigns[selectedImageIndex].name,
                            )
                            if(campaigns[selectedImageIndex].state == CampaignState.DEVELOPMENT){
                                Text(
                                    text = stringResource(R.string.in_development),
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        } else {
                            Text(
                                text = campaigns[selectedImageIndex].name,
                            )
                        }
                    }
                }
            }
            if (selectedImageIndex < campaigns.lastIndex) {
                RoundedImageButton(
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