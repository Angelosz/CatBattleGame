package angelosz.catbattlegame.ui.archives.composables

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import angelosz.catbattlegame.R
import angelosz.catbattlegame.ui.theme.CatBattleGameTheme

@Composable
fun EnemyCatArchiveCard(
    modifier: Modifier = Modifier,
    id: Int = 1,
    @DrawableRes image: Int,
    onCardClicked: (Int) -> Unit = {},
    imageSize: Int = 127,
    showBorder: Boolean = true,
    @DrawableRes borderImage: Int = R.drawable.cat_image_border,
    maskImage: Boolean = false
){
    Box(modifier = modifier
        .size(imageSize.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            onClick = { if(!maskImage) onCardClicked(id) },
        ) {
            if(maskImage){
                Box{
                    Image(
                        painter = painterResource(image),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(imageSize.dp)
                    )
                    Card(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size((imageSize / 2).dp)
                            .shadow(8.dp, CircleShape),
                        shape = CircleShape,
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Lock,
                            contentDescription = "Locked Chapter",
                            modifier = Modifier.padding(4.dp)
                                .size((imageSize / 2).dp),
                        )
                    }
                }
            } else {
                Image(
                    painter = painterResource(image),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(imageSize.dp)
                )
            }

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
fun EnemyCatArchiveCardPreview(){
    CatBattleGameTheme {
        EnemyCatArchiveCard(
            modifier = Modifier.padding(8.dp),
            id = 0,
            image = R.drawable.kitten_swordman_300x300,
            onCardClicked = {},
            imageSize = 128
        )
    }
}