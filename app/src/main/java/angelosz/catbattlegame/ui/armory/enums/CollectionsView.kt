package angelosz.catbattlegame.ui.armory.enums

import androidx.annotation.Keep
import kotlinx.serialization.Serializable


@Serializable
@Keep
enum class CollectionsView{
    CATS,
    TEAMS,
    BATTLE_CHESTS,
    ABILITIES,
    ENEMIES
}