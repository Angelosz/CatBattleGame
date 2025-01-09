package angelosz.catbattlegame.ui.home.notifications

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import angelosz.catbattlegame.domain.enums.BattleChestType
import angelosz.catbattlegame.domain.enums.CatRarity

sealed class HomeNotification(open val id: Long){
    abstract fun Display(): @Composable () -> Unit
}

class LevelUpNotification(
    override val id: Long,
    val name: String,
    @DrawableRes val image: Int,
    val level: Int
): HomeNotification(id){
    override fun Display(): @Composable () -> Unit {
        TODO("Not yet implemented")
    }
}

class CatEvolutionNotification(
    override val id: Long,
    val name: String,
    @DrawableRes val image: Int,
    val evolutionName: String,
    @DrawableRes val evolutionImage: Int,
): HomeNotification(id){
    override fun Display(): @Composable () -> Unit {
        TODO("Not yet implemented")
    }
}

class BattleChestNotification(
    override val id: Long,
    val rarity: CatRarity,
    val type: BattleChestType,
    val text: String,
): HomeNotification(id){
    override fun Display(): @Composable () -> Unit {
        TODO("Not yet implemented")
    }
}

class CurrencyRewardNotification(
    override val id: Long,
    @DrawableRes val image: Int,
    val amount: Int,
    val notificationText: String
): HomeNotification(id){
    override fun Display(): @Composable () -> Unit {
        TODO("Not yet implemented")
    }
}