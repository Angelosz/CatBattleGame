package angelosz.catbattlegame.domain.enums

enum class ArmorType(private val displayName: String) {
    LIGHT("Light"),
    MEDIUM("Medium"),
    HEAVY("Heavy"),
    HEROIC("Heroic"),
    ELEMENTAL("Elemental"),
    ETHEREAL("Ethereal");

    override fun toString(): String {
        return displayName
    }
}