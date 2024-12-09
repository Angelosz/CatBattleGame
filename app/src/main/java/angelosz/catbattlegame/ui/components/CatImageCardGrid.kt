package angelosz.catbattlegame.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import angelosz.catbattlegame.R
import angelosz.catbattlegame.ui.teambuilder.BasicCatData

@Composable
fun CatImageCardGrid(
    catsData: List<BasicCatData>,
    pageLimit: Int,
    imageSize: Int,
    onCardSelected: (Int) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.FixedSize(imageSize.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp, start = 12.dp, end = 12.dp, bottom = 4.dp)
    ) {

        val data = catsData.toMutableList()

        /* Fill the list with empty(catId = -1) BasicCatData */
        if (data.size < pageLimit) {
            repeat(pageLimit - data.size) {
                data.add(
                    BasicCatData(
                        catId = -1,
                        image = R.drawable.battlechest_background_128
                    )
                )
            }
        }

        items(data) { catData ->
            if (catData.catId >= 0) {
                SmallImageCard(
                    id = catData.catId,
                    image = catData.image,
                    onCardClicked = onCardSelected,
                    imageSize = imageSize,
                    showBorder = true
                )
            } else {
                SmallImageCard(
                    id = 0,
                    image = R.drawable.battlechest_background_128,
                    onCardClicked = {},
                    imageSize = 112,
                    showBorder = true
                )
            }
        }
    }
}