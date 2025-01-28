package angelosz.catbattlegame.ui.home.notifications

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import angelosz.catbattlegame.R
import angelosz.catbattlegame.domain.enums.BattleChestType
import angelosz.catbattlegame.domain.enums.CatRarity
import angelosz.catbattlegame.domain.enums.RewardType
import angelosz.catbattlegame.ui.components.BackgroundImage
import angelosz.catbattlegame.ui.components.RoundedImageButton
import angelosz.catbattlegame.ui.components.RoundedTextButton
import angelosz.catbattlegame.ui.theme.CatBattleGameTheme

sealed class HomeNotification(open val id: Long, open val type: NotificationType){
    abstract fun display(onAccept: () -> Unit): @Composable() (() -> Unit)
}

class LevelUpNotification(
    override val id: Long = 0,
    @StringRes val name: Int,
    @DrawableRes val image: Int,
    val level: Int,
    val notificationText: String
): HomeNotification(id, type = NotificationType.LEVEL_UP){
    override fun display(onAccept: () -> Unit): @Composable() (() -> Unit) = {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ){
            Card(
                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.tertiaryContainer)
            ) {
                Text(
                    text = stringResource(R.string.notification_level_up, stringResource(name)),
                    modifier = Modifier.padding(8.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Image(
                painter = painterResource(image),
                contentDescription = stringResource(name),
                modifier = Modifier
                    .size(192.dp)
                    .clickable(onClick = { onAccept() })
            )
            Box(
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.iconflash_256),
                    contentDescription = null,
                    modifier = Modifier
                        .size(96.dp)
                        .align(Alignment.BottomCenter)
                )
                RoundedTextButton(
                    size = 48,
                    text = "$level",
                    textStyle = MaterialTheme.typography.displaySmall,
                    onClick = { onAccept() }
                )
            }
        }
    }
}

class CatEvolutionNotification(
    override val id: Long = 0,
    @StringRes val name: Int,
    @DrawableRes val image: Int,
    @StringRes val evolutionName: Int,
    @DrawableRes val evolutionImage: Int,
    val notificationText: String
): HomeNotification(id, type = NotificationType.CAT_EVOLUTION){
    override fun display(onAccept: () -> Unit): @Composable() (() -> Unit) = {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.clickable(onClick = { onAccept() })
        ){
            Card(
                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.tertiaryContainer)
            ) {
                Text(
                    text = stringResource(R.string.notification_evolved, stringResource(name)),
                    modifier = Modifier
                        .padding(8.dp)
                        .width(256.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium,
                )
            }
            Image(
                painter = painterResource(evolutionImage),
                contentDescription = stringResource(evolutionName),
                modifier = Modifier.size(192.dp)
            )
            Card(
                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondaryContainer)
            ) {
                Text(
                    text = if(notificationText.isNotEmpty()) "\n$notificationText"
                            else "",
                    modifier = Modifier
                        .padding(8.dp)
                        .width(256.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }
    }
}

class BattleChestNotification(
    override val id: Long = 0,
    val rarity: CatRarity,
    val battleChestType: BattleChestType,
    val notificationText: String,
): HomeNotification(id, type = NotificationType.BATTLE_CHEST_REWARD){
    override fun display(onAccept: () -> Unit): @Composable() (() -> Unit) = {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Card(
                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.tertiaryContainer)
            ) {
                Text(
                    text = stringResource(R.string.notification_new_package),
                    modifier = Modifier.padding(8.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium
                )
            }

            Image(
                painter = painterResource(R.drawable.battlechest_512),
                contentDescription = null,
                modifier = Modifier
                    .padding(16.dp)
                    .size(192.dp)
                    .clickable(onClick = { onAccept() })
            )
            if(notificationText.isNotEmpty())
                Card(
                    colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondaryContainer)
                ) {
                    Text(
                        text = notificationText,
                        modifier = Modifier.padding(8.dp),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
        }
    }
}

class CurrencyRewardNotification(
    override val id: Long = 0,
    val amount: Int,
    val currencyType: RewardType,
    val notificationText: String
): HomeNotification(id, type = NotificationType.CURRENCY_REWARD){
    override fun display(onAccept: () -> Unit): @Composable () -> Unit = {
        Column(
            modifier = Modifier.clickable(onClick = {  onAccept() }),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Card(
                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.tertiaryContainer)
            ) {
                Text(
                    text = stringResource(
                        R.string.notification_currency, amount, when (currencyType) {
                            RewardType.GOLD -> stringResource(R.string.gold_coins)
                            RewardType.CRYSTAL -> stringResource(R.string.crystals)
                            else -> stringResource(R.string.gold_coins)
                        }
                    ),
                    modifier = Modifier.padding(8.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium
                )
            }

            RoundedImageButton(
                onClick = { onAccept() },
                innerImage =
                    when(currencyType){
                        RewardType.GOLD -> R.drawable.goldcoins_128
                        RewardType.CRYSTAL -> R.drawable.crystals_128
                        else -> R.drawable.goldcoins_128
                    },
                innerImageSize = 128,
                outerImage = R.drawable.iconflash_256,
                outerImageSize = 192,
            )

            if(notificationText.isNotEmpty()){
                Card(
                    colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondaryContainer)
                ) {
                    Text(
                        text = notificationText,
                        modifier = Modifier.padding(8.dp),
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CurrencyPreview(){
    CatBattleGameTheme() {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            BackgroundImage(R.drawable.homescreen_portrait_blurry)
            CurrencyRewardNotification(
                id = 0,
                amount = 10,
                currencyType = RewardType.CRYSTAL,
                notificationText = "Daily Reward"
            ).display({})()
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LevelUpPreview(){
    CatBattleGameTheme() {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            BackgroundImage(R.drawable.homescreen_portrait_blurry)
            LevelUpNotification(
                id = 0,
                name = R.string.the_swordsman_name,
                image = R.drawable.kitten_swordman_300x300,
                level = 3,
                notificationText = "Level Up"
            ).display({})()

        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BattleChestPreview(){
    CatBattleGameTheme() {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            BackgroundImage(R.drawable.homescreen_portrait_blurry)
            BattleChestNotification(
                id = 0,
                rarity = CatRarity.KITTEN,
                battleChestType = BattleChestType.NORMAL,
                notificationText = "Weekly Reward"
            ).display({})()

        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun EvolutionPreview(){
    CatBattleGameTheme() {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            BackgroundImage(R.drawable.homescreen_portrait_blurry)
            CatEvolutionNotification(
                id = 0,
                name = R.string.the_swordsman_name,
                image = R.drawable.kitten_swordman_300x300,
                evolutionName = R.string.the_swordsman_name,
                evolutionImage = R.drawable.teen_swordman_300x300,
                notificationText = "was already in your armory, you gained 40 crystals instead."
            ).display({})()

        }
    }
}