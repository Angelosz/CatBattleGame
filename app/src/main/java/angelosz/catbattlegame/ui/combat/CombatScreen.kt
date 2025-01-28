package angelosz.catbattlegame.ui.combat

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import angelosz.catbattlegame.CatViewModelProvider
import angelosz.catbattlegame.R
import angelosz.catbattlegame.domain.enums.CombatResult
import angelosz.catbattlegame.domain.enums.CombatStage
import angelosz.catbattlegame.domain.enums.CombatState
import angelosz.catbattlegame.domain.enums.ScreenState
import angelosz.catbattlegame.ui.components.BackgroundImage
import angelosz.catbattlegame.ui.components.CatCard
import angelosz.catbattlegame.ui.components.FailureCard
import angelosz.catbattlegame.ui.components.LoadingCard

@Composable
fun CombatScreen(
    chapterId: Long,
    teamId: Long,
    windowSize: WindowWidthSizeClass,
    onBackToTeamSelection: () -> Unit,
    onCombatFinished: (Long, Long, CombatResult) -> Unit,
    viewModel: CombatScreenViewModel = viewModel(factory = CatViewModelProvider.Factory)
) {
    BackHandler(onBack = { })

    val uiState by viewModel.uiState.collectAsState()
    val isPortraitView = windowSize != WindowWidthSizeClass.Expanded

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        BackgroundImage(R.drawable.encyclopedia_landscape_blurry)

        when(uiState.screenState){
            ScreenState.SUCCESS -> {
                when(uiState.combatState){
                    CombatState.NOT_LOADED -> {
                        LaunchedEffect(chapterId, teamId){
                            viewModel.setupTeamsData(chapterId, teamId)
                        }
                    }
                    CombatState.READY_TO_START -> {
                        BackHandler(onBack = onBackToTeamSelection)
                        val enemyTeam = uiState.chapterEnemies
                        val playerTeam = uiState.team.cats

                        Column(
                            modifier = Modifier
                                .fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ){
                            Column(
                                modifier = Modifier
                                    .weight(0.375f)
                                    .fillMaxSize(),
                                verticalArrangement = Arrangement.Bottom,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                TeamPortraitGrid(
                                    gridItem = { index ->
                                        val catData = enemyTeam.getOrNull(index)
                                        if (catData != null) {
                                            CatCard(
                                                modifier = Modifier.padding(8.dp),
                                                id = catData.id.toInt(),
                                                image = catData.image,
                                            )
                                        }
                                    },
                                    columns = if(isPortraitView) 2 else 4
                                )
                            }

                            Box(
                                modifier = Modifier.weight(0.15f),
                                contentAlignment = Alignment.Center
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Button(
                                        onClick = onBackToTeamSelection
                                    ) {
                                        Text(
                                            modifier = Modifier.padding(8.dp),
                                            text = stringResource(R.string.change_team)
                                        )
                                    }
                                    Button(
                                        onClick = { viewModel.startCombat() }
                                    ) {
                                        Text(
                                            modifier = Modifier.padding(8.dp),
                                            text = stringResource(R.string.start_fight)
                                        )
                                    }
                                }
                            }

                            Column(
                                modifier = Modifier
                                    .weight(0.375f)
                                    .fillMaxSize(),
                                verticalArrangement = Arrangement.Top,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                TeamPortraitGrid(
                                    gridItem = { index ->
                                        val catData = playerTeam.getOrNull(index)
                                        if (catData != null) {
                                            CatCard(
                                                modifier = Modifier.padding(4.dp),
                                                id = catData.catId,
                                                image = catData.image,
                                            )
                                        }
                                    },
                                    columns = if(isPortraitView) 2 else 4
                                )
                            }
                        }
                    }
                    CombatState.IN_PROGRESS -> {
                        if(isPortraitView){
                            Column(
                                modifier = Modifier
                                    .fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ){
                                val selectedCats = uiState.activeAbility?.getSelectedCatsIds() ?: emptyList()

                                EnemyTeamDisplay(
                                    modifier = Modifier
                                        .weight(0.35f)
                                        .fillMaxSize(),
                                    uiState = uiState,
                                    selectedCats = selectedCats,
                                    viewModel = viewModel,
                                    verticalArrangement = Arrangement.Bottom
                                )

                                InitiativeDisplay(
                                    modifier = Modifier
                                        .weight(0.075f)
                                        .width(320.dp),
                                    uiState = uiState
                                )
                                AbilitiesDisplay(
                                    modifier = Modifier.weight(0.1f),
                                    uiState = uiState,
                                    viewModel = viewModel)
                                PlayerTeamDisplay(
                                    modifier = Modifier
                                        .weight(0.35f)
                                        .fillMaxSize(),
                                    uiState = uiState,
                                    selectedCats = selectedCats,
                                    viewModel = viewModel,
                                    verticalArrangement = Arrangement.Top
                                )
                            }
                        } else {
                            Row(
                                modifier = Modifier
                                    .fillMaxSize(),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ){
                                val selectedCats = uiState.activeAbility?.getSelectedCatsIds() ?: emptyList()

                                PlayerTeamDisplay(
                                    modifier = Modifier
                                        .weight(0.35f)
                                        .fillMaxSize(),
                                    uiState = uiState,
                                    selectedCats = selectedCats,
                                    viewModel = viewModel,
                                    verticalArrangement = Arrangement.Center
                                )

                                Column(
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    InitiativeDisplay(
                                        modifier = Modifier
                                            .width(320.dp),
                                        uiState = uiState
                                    )
                                    AbilitiesDisplay(
                                        modifier = Modifier.width(320.dp),
                                        uiState = uiState,
                                        viewModel = viewModel)
                                }

                                EnemyTeamDisplay(
                                    modifier = Modifier
                                        .weight(0.35f)
                                        .fillMaxSize(),
                                    uiState = uiState,
                                    selectedCats = selectedCats,
                                    viewModel = viewModel,
                                    verticalArrangement = Arrangement.Center
                                )
                            }
                        }

                    }
                    CombatState.FINISHED -> {
                        LaunchedEffect(chapterId, teamId, uiState.combatResult){
                            onCombatFinished(teamId, chapterId, uiState.combatResult)
                        }
                    }
                    CombatState.PAUSED -> {}
                    CombatState.LOADING -> {}
                }
            }
            ScreenState.LOADING -> {
                LoadingCard()
            }
            ScreenState.FAILURE -> {
                FailureCard(
                    onBackPressed = onBackToTeamSelection,
                    onReloadPressed = {}
                )
            }
            ScreenState.INITIALIZING -> {
                LaunchedEffect(chapterId, teamId){
                    viewModel.setupTeamsData(chapterId, teamId)
                }
            }
        }
    }
}

@Composable
private fun PlayerTeamDisplay(
    modifier: Modifier = Modifier,
    uiState: CombatScreenUiState,
    selectedCats: List<Int>,
    viewModel: CombatScreenViewModel,
    verticalArrangement: Arrangement.Vertical,
) {
    Column(
        modifier = modifier,
        verticalArrangement = verticalArrangement,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TeamPortraitGrid(
            gridItem = { index ->
                val catData = uiState.teamCombatCats.getOrNull(index)
                val border =
                    if (catData?.cat?.combatId in selectedCats) {
                        if (viewModel.isAiTurn()) {
                            R.drawable.small_border_enemy_selected
                        } else {
                            R.drawable.small_border_ally_selected
                        }
                    } else {
                        if (uiState.activeCatId == catData?.cat?.combatId) {
                            R.drawable.cat_image_border_active
                        } else {
                            R.drawable.cat_image_border
                        }
                    }
                catData?.Display(
                    imageSize = 128,
                    barHeight = 8,
                    onCardClicked = { viewModel.allyClicked(catData.cat.combatId) },
                    border = border
                )
            }
        )
    }
}

@Composable
private fun EnemyTeamDisplay(
    modifier: Modifier = Modifier,
    uiState: CombatScreenUiState,
    selectedCats: List<Int>,
    viewModel: CombatScreenViewModel,
    verticalArrangement: Arrangement.Vertical,
) {
    Column(
        modifier = modifier,
        verticalArrangement = verticalArrangement,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TeamPortraitGrid(
            gridItem = { index ->
                val catData = uiState.enemyCombatCats.getOrNull(index)
                val border =
                    if (uiState.activeCatId == catData?.cat?.combatId) {
                        R.drawable.cat_image_border_active
                    } else {
                        if (catData?.cat?.combatId in selectedCats) {
                            if (viewModel.isAiTurn()) {
                                R.drawable.small_border_ally_selected
                            } else {
                                R.drawable.small_border_enemy_selected
                            }
                        } else {
                            R.drawable.cat_image_border
                        }
                    }
                catData?.Display(
                    imageSize = 128,
                    barHeight = 8,
                    onCardClicked = { viewModel.enemyClicked(catData.cat.combatId) },
                    border = border
                )
            }
        )
    }
}

@Composable
private fun AbilitiesDisplay(
    modifier: Modifier = Modifier,
    uiState: CombatScreenUiState,
    viewModel: CombatScreenViewModel,
) {
    Card(
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface)
    ) {
        Text(
            text = stringResource(uiState.abilityMessage),
            modifier = Modifier
                .padding(4.dp)
                .width(256.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.labelLarge
        )
    }
    val activeCat = viewModel.playerCatIsActive()
    if (activeCat != null) {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (activeCat.isStunned()) {
                Card(
                    modifier = Modifier.padding(2.dp),
                    colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(R.string.your_cat_is_stunned),
                            modifier = Modifier
                                .padding(2.dp)
                                .width(256.dp),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.labelLarge
                        )
                        Button(
                            modifier = Modifier.padding(2.dp),
                            onClick = { if (uiState.combatStage == CombatStage.PLAYER_TURN) viewModel.passedTurn() }
                        ) {
                            Text(
                                modifier = Modifier.padding(4.dp),
                                text = stringResource(R.string.end_turn)
                            )
                        }
                    }
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.padding(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(
                        4.dp,
                        Alignment.CenterHorizontally
                    ),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(activeCat.cat.abilities) { ability ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clickable(onClick = {
                                    if (!ability.onCooldown() && uiState.combatStage != CombatStage.ENDING_TURN) {
                                        viewModel.abilityClicked(ability)
                                    }
                                })
                                .background(
                                    color = when {
                                        ability.onCooldown() -> Color.DarkGray
                                        ability == uiState.activeAbility -> Color.LightGray
                                        else -> Color.White
                                    }
                                )
                        ) {
                            Box(
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(ability.ability.icon),
                                    contentDescription = stringResource(
                                        R.string.ability_icon_desc,
                                        stringResource(ability.ability.name)
                                    ),
                                    modifier = Modifier.size(40.dp),
                                )
                                if (ability.onCooldown()) {
                                    Text(
                                        text = "${ability.getCooldownRemaining()}",
                                        color = Color.White,
                                        style = MaterialTheme.typography.headlineLarge.copy(
                                            shadow = Shadow(
                                                color = Color.Black,
                                                offset = Offset(3f, 3f),
                                                blurRadius = 1f
                                            )
                                        ),
                                        fontWeight = FontWeight.Bold,
                                    )
                                }
                            }
                            Text(
                                text = stringResource(ability.ability.name),
                                modifier = Modifier.padding(2.dp)
                            )
                        }
                    }
                }
            }
        }
    } else {
        Spacer(modifier = modifier)
    }
}

@Composable
private fun InitiativeDisplay(modifier: Modifier = Modifier, uiState: CombatScreenUiState) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        HorizontalDivider(
            thickness = 4.dp
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "<<-",
                style = MaterialTheme.typography.displaySmall,
                color = Color.White
            )
            uiState.catInitiatives.forEach { cat ->
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(cat.image),
                        contentDescription = null,
                        modifier = Modifier
                            .size(48.dp)
                            .padding(horizontal = 2.dp)
                    )
                    Text(
                        modifier = Modifier.align(Alignment.BottomCenter),
                        text = "%.2f".format(cat.initiative),
                        color = Color.White,
                        style = MaterialTheme.typography.labelMedium.copy(
                            shadow = Shadow(
                                color = Color.Black,
                                offset = Offset(3f, 3f),
                                blurRadius = 1f
                            ),
                        )
                    )
                }
            }
        }
    }
}

@Composable
private fun TeamPortraitGrid(
    gridItem: @Composable (Int) -> Unit = {},
    columns: Int = 2
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
    ) {
        repeat(columns) { index ->
            gridItem(index)
        }
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
    ) {
        repeat(columns) { index ->
            gridItem(index + columns)
        }
    }
}