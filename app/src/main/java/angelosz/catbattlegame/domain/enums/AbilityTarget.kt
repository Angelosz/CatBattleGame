package angelosz.catbattlegame.domain.enums

import androidx.annotation.StringRes
import angelosz.catbattlegame.R

enum class AbilityTarget(@StringRes val res: Int){
    SINGLE_ENEMY(R.string.ability_target_single_enemy),
    ENEMY_TEAM(R.string.ability_target_enemy_team),
    ALLY(R.string.ability_target_ally),
    TEAM(R.string.ability_target_team);
}