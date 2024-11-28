package angelosz.catbattlegame.domain.enums

enum class AbilityTarget(private val displayName: String){
    SINGLE_ENEMY("Single enemy"),
    ENEMY_TEAM("Enemy team"),
    ALLY("Ally"),
    TEAM("Team");

    override fun toString(): String {
        return displayName
    }
}