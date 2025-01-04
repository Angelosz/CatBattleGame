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
import angelosz.catbattlegame.ui.archives.data.SimpleCatData
import angelosz.catbattlegame.ui.components.CatCard
import angelosz.catbattlegame.ui.components.PaginationButtons

@Composable
fun ArchiveCatGrid(
    modifier: Modifier = Modifier,
    cats: List<SimpleCatData>,
    onCatCardClicked: (SimpleCatData) -> Unit,
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
                if(index < cats.size){
                    CatCard(
                        id = cats[index].id,
                        image = cats[index].image,
                        onCardClicked = { onCatCardClicked(cats[index]) },
                        imageSize = imageSize,
                        showBorder = true
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ArmoryCatsViewPreview(){
    Column(modifier = Modifier
        .width(384.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        ArchiveCatGrid(
            modifier = Modifier
                .padding(8.dp),
            cats = listOf(
                SimpleCatData(1, R.drawable.kitten_swordman_300x300),
                SimpleCatData(2, R.drawable.kitten_mage_300),
                SimpleCatData(3, R.drawable.kitten_rogue_300),
                SimpleCatData(4, R.drawable.kitten_priest_300),
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