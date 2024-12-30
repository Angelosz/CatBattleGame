package angelosz.catbattlegame.ui.armory.cats_view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import angelosz.catbattlegame.CatViewModelProvider
import angelosz.catbattlegame.R
import angelosz.catbattlegame.data.entities.Ability
import angelosz.catbattlegame.domain.enums.ArmorType
import angelosz.catbattlegame.domain.enums.CatRarity
import angelosz.catbattlegame.domain.enums.CatRole
import angelosz.catbattlegame.domain.enums.ScreenState
import angelosz.catbattlegame.ui.components.CatCard
import angelosz.catbattlegame.ui.components.CatCardWithExperienceBar
import angelosz.catbattlegame.ui.components.ExperienceBar
import angelosz.catbattlegame.ui.components.FailureCard
import angelosz.catbattlegame.ui.components.LoadingCard
import angelosz.catbattlegame.ui.components.PaginationButtons
import angelosz.catbattlegame.ui.components.RoundedTextButton
import angelosz.catbattlegame.ui.theme.CatBattleGameTheme

@Composable
fun ArmoryCatsView(
    viewModel: ArmoryCatsViewModel = viewModel(factory = CatViewModelProvider.Factory),
    innerPadding: PaddingValues,
    onFailure: () -> Unit,
    pageLimit: Int = 12,
    isLandscapeView: Boolean = false
) {
    val uiState by viewModel.uiState.collectAsState()

    when(uiState.state){
        ScreenState.SUCCESS -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ){
                if(isLandscapeView){
                    HandleArmoryCatsLandscapeView(uiState, viewModel)
                } else {
                    HandleArmoryCatsPortraitView(uiState, viewModel)
                }
            }
        }
        ScreenState.LOADING -> {
            LoadingCard()
        }
        ScreenState.FAILURE -> {
            FailureCard(onFailure)
        }
        ScreenState.INITIALIZING -> {
            LoadingCard()
            LaunchedEffect(viewModel){
                viewModel.setup(pageLimit)
            }
        }
    }
}

@Composable
fun HandleArmoryCatsLandscapeView(
    uiState: ArmoryCatsUiState,
    viewModel: ArmoryCatsViewModel
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .weight(1f),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Center
            ) {
                ArmoryCatList(
                    modifier = Modifier
                        .padding(8.dp)
                        .width(448.dp),
                    cats = uiState.cats,
                    onCatCardClicked = viewModel::selectCat,
                    pageLimit = uiState.pageLimit,
                    imageSize = 96
                )
                PaginationButtons(
                    isNotFirstPage = uiState.page > 0,
                    isNotLastPage = viewModel.isNotLastPage(),
                    onPreviousButtonClicked = viewModel::goToPreviousPage,
                    onNextButtonClicked = viewModel::goToNextPage
                )
            }
            ArmoryCatDetailsCard(
                modifier = Modifier.width(368.dp),
                cat = uiState.selectedCat,
                onCloseClicked = viewModel::exitDetailView,
            )
        }
    }
}

@Composable
private fun HandleArmoryCatsPortraitView(
    uiState: ArmoryCatsUiState,
    viewModel: ArmoryCatsViewModel,
) {
    if (uiState.isDetailView) {
        ArmoryCatDetailsCard(
            modifier = Modifier.padding(16.dp),
            cat = uiState.selectedCat,
            onCloseClicked = viewModel::exitDetailView
        )
    } else {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ArmoryCatList(
                modifier = Modifier.padding(8.dp),
                cats = uiState.cats,
                onCatCardClicked = viewModel::selectCat,
                pageLimit = uiState.pageLimit,
                imageSize = 112
            )
            PaginationButtons(
                isNotFirstPage = uiState.page > 0,
                isNotLastPage = viewModel.isNotLastPage(),
                onPreviousButtonClicked = viewModel::goToPreviousPage,
                onNextButtonClicked = viewModel::goToNextPage
            )
        }
    }
}

@Composable
fun ArmoryCatList(
    modifier: Modifier = Modifier,
    cats: List<SimpleArmoryCatData>,
    onCatCardClicked: (Int) -> Unit,
    pageLimit: Int = 9,
    imageSize: Int = 96
) {
    LazyVerticalGrid(
        columns = GridCells.FixedSize(imageSize.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
        modifier = modifier
    ){
        repeat(pageLimit) { index ->
            item {
                if(index < cats.size){
                    CatCardWithExperienceBar(
                        id = cats[index].id,
                        level = cats[index].level,
                        experience = cats[index].experience,
                        image = cats[index].image,
                        imageSize = imageSize,
                        onCardClicked = onCatCardClicked,
                    )
                } else {
                    CatCard(
                        id = 0,
                        image = R.drawable.battlechest_background_128,
                        onCardClicked = {},
                        imageSize = imageSize,
                        showBorder = true
                    )
                }
            }
        }
    }
}

@Composable
fun ArmoryCatDetailsCard(
    modifier: Modifier = Modifier,
    cat: DetailedArmoryCatData,
    showCloseButton: Boolean = false,
    onCloseClicked: () -> Unit,
) {
    Card(
        modifier = modifier,
        shape = RectangleShape
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            ArmoryCatDetailsCatContent(cat)

            if(showCloseButton){
                RoundedTextButton(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp),
                    text = "Hide",
                    size = 64,
                    onClick = onCloseClicked,
                    textStyle = MaterialTheme.typography.headlineSmall
                )
            }

        }
    }
}

@Composable
private fun ArmoryCatDetailsCatContent(
    cat: DetailedArmoryCatData,
    imageSize: Int = 300
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = cat.name,
            modifier = Modifier.padding(16.dp),
            color = Color.White,
            style = MaterialTheme.typography.headlineSmall.copy(
                shadow = Shadow(
                    color = Color.Black,
                    offset = Offset(3f, 3f),
                    blurRadius = 1f
                )
            ),
            fontWeight = FontWeight.Bold,
        )
        Image(
            painter = painterResource(cat.image),
            contentDescription = cat.name,
            modifier = Modifier
                .fillMaxWidth()
                .height(imageSize.dp),
        )
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            ExperienceBar(
                modifier = Modifier.padding(horizontal = 32.dp),
                level = cat.level,
                experience = cat.experience,
                showText = true
            )
            Text(
                text = "\"${cat.description}\"",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                textAlign = TextAlign.Center
            )
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                modifier = Modifier.padding(4.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Column {
                        Text(
                            text = "Base Health: ${cat.health}",
                            modifier = Modifier.padding(4.dp),
                        )
                        Text(
                            text = "Base Defense: ${cat.defense}",
                            modifier = Modifier.padding(4.dp)
                        )
                        Text(
                            text = "Role: ${stringResource(cat.role.res)}",
                            modifier = Modifier.padding(4.dp)
                        )

                        Text(
                            text = "Armor type: ${stringResource(cat.armorType.res)}",
                            modifier = Modifier.padding(4.dp)
                        )

                    }
                    Column {
                        Text(
                            text = "Base Attack: ${cat.attack}",
                            modifier = Modifier.padding(4.dp)
                        )
                        Text(
                            text = "Attack Speed: ${cat.speed}s",
                            modifier = Modifier.padding(4.dp)
                        )
                        Text(
                            text = "Rarity: ${stringResource(cat.rarity.res)}",
                            modifier = Modifier.padding(4.dp)
                        )
                    }
                }
            }

            if (cat.abilities.isNotEmpty()) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth()
                ) {
                    for (ability in cat.abilities) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(8.dp)
                        ) {
                            Image(
                                painter = painterResource(ability.icon),
                                contentDescription = "${ability.name} ability icon",
                                modifier = Modifier.size(48.dp)
                            )
                            Text(
                                text = ability.name,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ArmoryCatsViewPreview(){
    Column(modifier = Modifier
        .width(384.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        ArmoryCatList(
            modifier = Modifier
                .padding(8.dp),
            cats = listOf(
                SimpleArmoryCatData(1, R.drawable.kitten_swordman_300x300, 1, 20),
                SimpleArmoryCatData(2, R.drawable.kitten_mage_300, 2, 30),
                SimpleArmoryCatData(3, R.drawable.kitten_rogue_300, 1, 40),
                SimpleArmoryCatData(4, R.drawable.kitten_priest_300, 1, 50),
            ),
            onCatCardClicked = {},
            pageLimit = 9,
            imageSize = 112
        )
        PaginationButtons(
            isNotFirstPage = false,
            isNotLastPage = true,
            onPreviousButtonClicked = {},
            onNextButtonClicked = {}
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ArmoryCatDetailsCardPreview(){
    CatBattleGameTheme {
        ArmoryCatDetailsCard(
            cat = DetailedArmoryCatData(
                id = 1,
                name = "Kitten Swordsman",
                description = "A brave kitten wielding a wooden sword, eager to protect.",
                image = R.drawable.kitten_swordman_300x300,
                level = 1,
                experience = 0,
                health = 50,
                attack = 15,
                defense = 10,
                speed = 1.2f,
                role = CatRole.WARRIOR,
                armorType = ArmorType.LIGHT,
                rarity = CatRarity.KITTEN,
                abilities = listOf(
                    Ability(
                        id = 1,
                        name = "Quick Attack",
                        icon = R.drawable.ability_quick_attack_48,
                    ),
                    Ability(
                        id = 2,
                        name = "Quick Attack",
                        icon = R.drawable.ability_quick_attack_48,
                    )
                )
            ),
            onCloseClicked = { }
        )
    }
}