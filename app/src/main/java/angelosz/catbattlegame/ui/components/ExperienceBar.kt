package angelosz.catbattlegame.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
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
import angelosz.catbattlegame.ui.theme.CatBattleGameTheme
import angelosz.catbattlegame.utils.GameConstants.EXPERIENCE_PER_LEVEL

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
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(levelCardSize.dp)
        ){
            RoundedTextButton(text = "$level", textStyle = MaterialTheme.typography.headlineSmall)
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
                    text = "$experience / ${EXPERIENCE_PER_LEVEL}",
                    color = Color.White
                )
            }
        }
    }
}

fun calculateExperience(experience: Int): Float
    = (experience.toFloat() / EXPERIENCE_PER_LEVEL.toFloat())

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