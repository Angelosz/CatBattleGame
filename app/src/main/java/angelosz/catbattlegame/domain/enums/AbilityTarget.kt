package angelosz.catbattlegame.domain.enums

import androidx.annotation.StringRes
import angelosz.catbattlegame.R

enum class AbilityTarget(@StringRes val res: Int){
    SINGLE_ENEMY(R.string.abilitytarget_single_enemy),
    ENEMY_TEAM(R.string.abilitytarget_enemy_team),
    ALLY(R.string.abilitytarget_ally),
    TEAM(R.string.abilitytarget_team);
}