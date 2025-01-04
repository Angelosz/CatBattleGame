package angelosz.catbattlegame.ui.archives.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import angelosz.catbattlegame.R
import angelosz.catbattlegame.ui.archives.data.SimpleArchiveEnemyData
import angelosz.catbattlegame.ui.components.PaginationButtons

@Composable
fun ArchiveEnemyGrid(
    modifier: Modifier = Modifier,
    enemies: List<SimpleArchiveEnemyData>,
    onCatCardClicked: (SimpleArchiveEnemyData) -> Unit,
    pageLimit: Int = 9,
    imageSize: Int = 96,
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.FixedSize(imageSize.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
    ){
        repeat(pageLimit) { index ->
            item {
                if(index < enemies.size){
                    EnemyCatArchiveCard(
                        id = enemies[index].id.toInt(),
                        image = enemies[index].image,
                        onCardClicked = { onCatCardClicked(enemies[index]) },
                        imageSize = imageSize,
                        showBorder = true,
                        maskImage = !enemies[index].isDiscovered
                    )
                } else {
                    EnemyCatArchiveCard(
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ArchiveEnemyGridPreview(){
    Column(modifier = Modifier
        .width(384.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        ArchiveEnemyGrid(
            modifier = Modifier
                .padding(8.dp),
            enemies = listOf(
                SimpleArchiveEnemyData(1, R.drawable.kitten_swordman_300x300, true),
                SimpleArchiveEnemyData(2, R.drawable.kitten_mage_300, true),
                SimpleArchiveEnemyData(3, R.drawable.kitten_rogue_300, false),
                SimpleArchiveEnemyData(4, R.drawable.kitten_priest_300, false),
            ),
            onCatCardClicked = {},
            pageLimit = 9,
            imageSize = 112,
        )
        PaginationButtons(
            isNotFirstPage = false,
            isNotLastPage = true,
            onPreviousButtonClicked = {},
            onNextButtonClicked = {}
        )
    }
}