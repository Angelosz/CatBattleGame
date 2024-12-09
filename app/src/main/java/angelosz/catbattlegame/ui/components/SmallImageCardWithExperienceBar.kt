package angelosz.catbattlegame.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import angelosz.catbattlegame.R
import angelosz.catbattlegame.ui.theme.CatBattleGameTheme

@Composable
fun SmallImageCardWithExperienceBar(
    modifier: Modifier = Modifier,
    id: Int,
    onCardClicked: (Int) -> Unit,
    imageSize: Int,
    image: Int,
    level: Int,
    experience: Int,
) {
    Box(
        modifier = modifier
            .width(imageSize.dp)
            .height((imageSize + 16).dp)
    ){
        SmallImageCard(
            modifier = Modifier,
            id = id,
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
        SmallImageCardWithExperienceBar(
            id = 21,
            onCardClicked = {},
            imageSize = 192,
            image = R.drawable.kitten_rogue_300,
            level = 1,
            experience = 200
        )
    }
}