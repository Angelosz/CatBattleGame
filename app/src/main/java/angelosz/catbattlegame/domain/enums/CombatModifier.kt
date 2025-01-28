package angelosz.catbattlegame.domain.enums

import androidx.annotation.StringRes
import angelosz.catbattlegame.R

enum class CombatModifier(@StringRes val res: Int){
    SLOWED(R.string.combat_modifier_slowed),
    STUNNED(R.string.combat_modifier_stunned),
    POISONED(R.string.combat_modifier_poisoned),
    CLEANSED(R.string.combat_modifier_cleansed),
    SHIELDED(R.string.combat_modifier_shielded),
    HEALTH_MODIFIED(R.string.combat_modifier_health),
    ATTACK_MODIFIED(R.string.combat_modifier_attack),
    DEFENSE_MODIFIED(R.string.combat_modifier_defense),
    SPEED_MODIFIED(R.string.combat_modifier_speed)
}