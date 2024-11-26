package angelosz.catbattlegame.domain.enums

enum class CatRole(private val displayName: String) {
    WARRIOR("Warrior"),
    MAGE("Mage"),
    HEALER("Healer"),
    DEFENDER("Defender");

    override fun toString(): String {
        return displayName
    }
}