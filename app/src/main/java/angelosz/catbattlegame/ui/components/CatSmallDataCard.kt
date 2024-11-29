package angelosz.catbattlegame.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import angelosz.catbattlegame.R
import angelosz.catbattlegame.ui.theme.CatBattleGameTheme

@Composable
fun CatSmallDataCard(modifier: Modifier = Modifier, id: Int, name: String, @DrawableRes image: Int, onCardClicked: (Int) -> Unit){
    Card(
        modifier = modifier.shadow(2.dp, RoundedCornerShape(4.dp)),
        onClick = { onCardClicked(id) }
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(8.dp)
        )
        Image(
            painter = painterResource(image),
            contentDescription = name,
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(176.dp)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CatSmallDataCardPreview(){
    CatBattleGameTheme {
        CatSmallDataCard(
            modifier = Modifier.padding(8.dp),
            id = 0,
            name = "Kitten Swordman",
            image = R.drawable.kitten_swordman_300x300,
            onCardClicked = {})
    }
}
