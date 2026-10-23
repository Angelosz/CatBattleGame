package angelosz.catbattlegame.ui.home.dialogs

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import angelosz.catbattlegame.R
import angelosz.catbattlegame.utils.SoundManager

data class DailyRewardDayItem(
    val dayNumber: Int,
    val title: String,
    val iconRes: Int,
    val isClaimed: Boolean,
    val isAvailable: Boolean
)

@Composable
fun DailyRewardDialog(
    streakDay: Int,
    isClaimableToday: Boolean,
    onClaimReward: () -> Unit,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val currentDay = if (streakDay in 1..7) streakDay else 1

    val days = listOf(
        DailyRewardDayItem(1, "50 Gold", R.drawable.goldcoins_128, currentDay > 1 || (currentDay == 1 && !isClaimableToday), currentDay == 1 && isClaimableToday),
        DailyRewardDayItem(2, "100 Gold", R.drawable.goldcoins_128, currentDay > 2 || (currentDay == 2 && !isClaimableToday), currentDay == 2 && isClaimableToday),
        DailyRewardDayItem(3, "Kitten Chest", R.drawable.battlechest_256, currentDay > 3 || (currentDay == 3 && !isClaimableToday), currentDay == 3 && isClaimableToday),
        DailyRewardDayItem(4, "200 Gold", R.drawable.goldcoins_128, currentDay > 4 || (currentDay == 4 && !isClaimableToday), currentDay == 4 && isClaimableToday),
        DailyRewardDayItem(5, "25 Crystals", R.drawable.crystals_128, currentDay > 5 || (currentDay == 5 && !isClaimableToday), currentDay == 5 && isClaimableToday),
        DailyRewardDayItem(6, "Teen Chest", R.drawable.battlechest_256, currentDay > 6 || (currentDay == 6 && !isClaimableToday), currentDay == 6 && isClaimableToday),
        DailyRewardDayItem(7, "400 Gold + 50 Crystals", R.drawable.crystals_128, currentDay > 7 || (currentDay == 7 && !isClaimableToday), currentDay == 7 && isClaimableToday)
    )

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier.width(360.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF2C1C13)),
            border = BorderStroke(3.dp, Color(0xFFFFD700))
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.daily_rewards_title),
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color(0xFFFFD700),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    days.take(6).chunked(3).forEach { rowDays ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            rowDays.forEach { item ->
                                DailyRewardCardItem(
                                    item = item,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }
                    DailyRewardCardItem(
                        item = days.last(),
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    if (isClaimableToday) {
                        Button(
                            onClick = {
                                SoundManager.playHealSound(context)
                                onClaimReward()
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32)),
                            border = BorderStroke(2.dp, Color(0xFFFFD700)),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Text(
                                text = stringResource(R.string.claim_reward),
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    Button(
                        onClick = {
                            SoundManager.playButtonClick(context)
                            onDismiss()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5C4033)),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.close),
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DailyRewardCardItem(
    item: DailyRewardDayItem,
    modifier: Modifier = Modifier
) {
    val bgColor = when {
        item.isAvailable -> Color(0xFF386638)
        item.isClaimed -> Color(0xFF222222)
        else -> Color(0xFF3B2D26)
    }
    val borderColor = when {
        item.isAvailable -> Color(0xFFFFD700)
        item.isClaimed -> Color(0xFF555555)
        else -> Color(0x608C9388)
    }

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = bgColor),
        border = BorderStroke(if (item.isAvailable) 2.dp else 1.dp, borderColor)
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Day ${item.dayNumber}",
                style = MaterialTheme.typography.labelSmall,
                color = if (item.isAvailable) Color(0xFFFFD700) else Color.LightGray,
                fontWeight = FontWeight.Bold
            )
            Image(
                painter = painterResource(item.iconRes),
                contentDescription = null,
                modifier = Modifier.size(36.dp).padding(vertical = 4.dp)
            )
            Text(
                text = if (item.isClaimed) "Claimed" else item.title,
                style = MaterialTheme.typography.labelSmall,
                color = if (item.isClaimed) Color.Gray else Color.White,
                textAlign = TextAlign.Center
            )
        }
    }
}
