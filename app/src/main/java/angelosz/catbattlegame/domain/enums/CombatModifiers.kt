package angelosz.catbattlegame.domain.enums

import androidx.annotation.StringRes
import angelosz.catbattlegame.R

enum class CombatModifiers(@StringRes val res: Int){
    SLOWED(R.string.combatmodifier_slowed),
    STUNNED(R.string.combatmodifier_stunned),
    POISONED(R.string.combatmodifier_poisoned),
    CLEANSED(R.string.combatmodifier_cleansed),
    SHIELDED(R.string.combatmodifier_shielded);
}