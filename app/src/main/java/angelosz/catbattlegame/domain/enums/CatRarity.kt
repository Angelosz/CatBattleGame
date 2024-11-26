package angelosz.catbattlegame.domain.enums

enum class CatRarity(private val displayName: String) {
    KITTEN("Kitten"),
    TEEN("Teen"),
    ADULT("Adult"),
    ELDER("Elder");

    override fun toString(): String {
        return displayName
    }
}