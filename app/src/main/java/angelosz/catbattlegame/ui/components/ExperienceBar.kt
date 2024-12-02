package angelosz.catbattlegame.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import angelosz.catbattlegame.GameConstants.XP_PER_LEVEL
import angelosz.catbattlegame.ui.theme.CatBattleGameTheme

@Composable
fun ExperienceBar(
    modifier: Modifier = Modifier,
    level: Int,
    experience: Int,
    levelCardSize: Int = 32,
    barHeight: Int = 16,
    showText: Boolean = false
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ){
        Card(
            shape = CircleShape,
            colors = CardDefaults.cardColors(
                containerColor = Color.LightGray
            ),
            modifier = Modifier.size(levelCardSize.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ){
                Text(
                    text = "$level",
                    style = MaterialTheme.typography.labelLarge,
                )
            }
        }
        Box(
            contentAlignment = Alignment.Center
        ){
            LinearProgressIndicator(
                progress = { calculateExperience(experience) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(barHeight.dp),
                color = Color.Blue,
                gapSize = 0.dp,
                strokeCap = StrokeCap.Square,
                trackColor = Color.LightGray,
            )
            if(showText){
                Text(
                    text = "$experience / ${XP_PER_LEVEL}",
                    color = Color.White
                )
            }
        }
    }
}

fun calculateExperience(experience: Int): Float
    = (experience.toFloat() / XP_PER_LEVEL.toFloat())

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ExperienceBarPreview(){
    CatBattleGameTheme {
        ExperienceBar(
            level = 1,
            experience = 500,
        )
    }
}