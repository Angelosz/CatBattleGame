package angelosz.catbattlegame.domain.enums

enum class AbilityType(private val displayName: String) {
    BLUNT("Blunt"),
    PIERCING("Piercing"),
    HEROIC("Heroic"),
    ELEMENTAL("Elemental"),
    ETHEREAL("Ethereal"),
    HEALING("Healing"),
    NORMAL("Normal"),
    STATUS_CHANGING("Status changing");

    override fun toString(): String {
        return displayName
    }
}