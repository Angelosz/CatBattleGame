package angelosz.catbattlegame.domain.enums

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Serializable
@Keep
enum class CombatResult {
    PLAYER_WON,
    PLAYER_LOST,
    TIED
}