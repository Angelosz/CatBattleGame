package angelosz.catbattlegame.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import angelosz.catbattlegame.R
import angelosz.catbattlegame.ui.theme.CatBattleGameTheme

@Composable
fun CatCard(
    modifier: Modifier = Modifier,
    id: Int = 1,
    @DrawableRes image: Int,
    onCardClicked: (Int) -> Unit = {},
    imageSize: Int = 127,
    showBorder: Boolean = true,
    @DrawableRes borderImage: Int = R.drawable.cat_image_border
){
    Box(modifier = modifier
        .size(imageSize.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            onClick = { onCardClicked(id) },
        ) {
            Image(
                painter = painterResource(image),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(imageSize.dp)
            )
        }
        if(showBorder){
            Image(
                painter = painterResource(borderImage),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.size(imageSize.dp)
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CatSmallDataCardPreview(){
    CatBattleGameTheme {
        CatCard(
            modifier = Modifier.padding(8.dp),
            id = 0,
            image = R.drawable.kitten_swordman_300x300,
            onCardClicked = {},
            imageSize = 128
            )
    }
}
