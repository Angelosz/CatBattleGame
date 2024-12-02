package angelosz.catbattlegame.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import angelosz.catbattlegame.R
import angelosz.catbattlegame.ui.theme.CatBattleGameTheme

@Composable
fun CollectionSmallCard(
    modifier: Modifier = Modifier,
    id: Int,
    name: String,
    onCardClicked: (Int) -> Unit,
    imageSize: Int,
    image: Int,
    level: Int,
    experience: Int,
) {
    Box(
        modifier = modifier
            .size(imageSize.dp)
    ){
        SmallDataCard(
            modifier = Modifier.padding(8.dp),
            id = id,
            name = name,
            onCardClicked = onCardClicked,
            imageSize = imageSize,
            image = image
        )
        ExperienceBar(
            modifier = Modifier.align(Alignment.BottomEnd),
            level = level,
            experience = experience,
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CollectionSmallCardPreview(){
    CatBattleGameTheme {
        CollectionSmallCard(
            id = 21,
            name = "Kitten Rogue",
            onCardClicked = {},
            imageSize = 192,
            image = R.drawable.kitten_rogue_300,
            level = 1,
            experience = 200
        )
    }
}