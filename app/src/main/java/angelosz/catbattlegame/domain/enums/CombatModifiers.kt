package angelosz.catbattlegame.domain.enums

enum class CombatModifiers(private val displayName: String){
    SLOWED("Slowed"),
    STUNNED("Stunned"),
    POISONED("Poisoned"),
    CLEANSED("Cleansed"),
    SHIELDED("Shielded");

    override fun toString(): String {
        return displayName
    }
}