package angelosz.catbattlegame.ui.combat

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import angelosz.catbattlegame.CatViewModelProvider
import angelosz.catbattlegame.R
import angelosz.catbattlegame.domain.enums.CombatResult
import angelosz.catbattlegame.domain.enums.CombatStage
import angelosz.catbattlegame.domain.enums.CombatState
import angelosz.catbattlegame.domain.enums.ScreenState
import angelosz.catbattlegame.ui.components.BackgroundImage
import angelosz.catbattlegame.ui.components.FailureCard
import angelosz.catbattlegame.ui.components.LoadingCard
import angelosz.catbattlegame.ui.components.SmallImageCard

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
                                    .weight(0.4f)
                                    .fillMaxSize(),
                                verticalArrangement = Arrangement.Bottom,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                if(isPortraitView)
                                {
                                    TeamPortraitGrid(
                                        gridItem = { index ->
                                            val catData = enemyTeam.getOrNull(index)
                                            if (catData != null) {
                                                SmallImageCard(
                                                    modifier = Modifier.padding(8.dp),
                                                    id = catData.id.toInt(),
                                                    image = catData.image,
                                                )
                                            }
                                        }
                                    )
                                }
                            }

                            Box(
                                modifier = Modifier.weight(0.1f),
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
                                            text = "Change Team"
                                        )
                                    }
                                    Button(
                                        onClick = { viewModel.startCombat() }
                                    ) {
                                        Text(
                                            modifier = Modifier.padding(8.dp),
                                            text = "Fight"
                                        )
                                    }
                                }
                            }

                            Column(
                                modifier = Modifier
                                    .weight(0.4f)
                                    .fillMaxSize(),
                                verticalArrangement = Arrangement.Top,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                if(isPortraitView)
                                {
                                    TeamPortraitGrid(
                                        gridItem = { index ->
                                            val catData = playerTeam.getOrNull(index)
                                            if (catData != null) {
                                                SmallImageCard(
                                                    modifier = Modifier.padding(8.dp),
                                                    id = catData.catId,
                                                    image = catData.image,
                                                )
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }
                    CombatState.IN_PROGRESS -> {
                        Column(
                            modifier = Modifier
                                .fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ){
                            val selectedCats = uiState.activeAbility?.getSelectedCatsIds() ?: emptyList()
                            Column(
                                modifier = Modifier
                                    .weight(0.35f)
                                    .fillMaxSize(),
                                verticalArrangement = Arrangement.Bottom,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ){
                                TeamPortraitGrid(
                                    gridItem = { index ->
                                        val catData = uiState.enemyCombatCats.getOrNull(index)
                                        val border =
                                            if(uiState.activeCatId == catData?.cat?.combatId){
                                                R.drawable.cat_image_border_active
                                            } else {
                                                if(catData?.cat?.combatId in selectedCats) {
                                                    if(viewModel.isAiTurn()){
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
                            Box(
                                modifier = Modifier
                                    .weight(0.1f)
                                    .width(320.dp),
                                contentAlignment = Alignment.Center
                            ){
                                HorizontalDivider(
                                    thickness = 4.dp
                                )
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Start
                                ){
                                    Text(
                                        text = "<<",
                                        style = MaterialTheme.typography.displayMedium,
                                        color = Color.White
                                    )
                                    uiState.catInitiatives.forEach { cat ->
                                        Column(
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            Text(
                                                text =  "%.2f".format(cat.initiative),
                                                style = MaterialTheme.typography.labelMedium,
                                            )
                                            Image(
                                                painter = painterResource(cat.image),
                                                contentDescription = null,
                                                modifier = Modifier
                                                    .size(48.dp)
                                                    .padding(horizontal = 2.dp)
                                            )
                                        }
                                    }
                                }
                            }

                            Column(
                                modifier = Modifier
                                    .weight(0.35f)
                                    .fillMaxSize(),
                                verticalArrangement = Arrangement.Top,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ){
                                TeamPortraitGrid(
                                    gridItem = { index ->
                                        val catData = uiState.teamCombatCats.getOrNull(index)
                                        val border =
                                            if(catData?.cat?.combatId in selectedCats){
                                                if(viewModel.isAiTurn()){
                                                    R.drawable.small_border_enemy_selected
                                                } else {
                                                    R.drawable.small_border_ally_selected
                                                }
                                            } else {
                                                if(uiState.activeCatId == catData?.cat?.combatId) {
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
                            val activeCat = viewModel.playerCatIsActive()
                            if(activeCat != null){
                                Column(
                                    modifier = Modifier.weight(0.2f)
                                ) {
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
                                                    .padding(8.dp)
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
                                                Image(
                                                    painter = painterResource(ability.ability.icon),
                                                    contentDescription = "${ability.ability.name} ability icon",
                                                    modifier = Modifier.size(48.dp),
                                                )
                                                Text(
                                                    text = ability.ability.name,
                                                    modifier = Modifier.padding(8.dp)
                                                )
                                            }
                                        }
                                    }
                                }
                            } else {
                                Spacer(modifier = Modifier.weight(0.2f))
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
private fun TeamPortraitGrid(
    gridItem: @Composable (Int) -> Unit = {}
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
    ) {
        repeat(2) { index ->
            gridItem(index)
        }
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
    ) {
        repeat(2) { index ->
            gridItem(index + 2)
        }
    }
}