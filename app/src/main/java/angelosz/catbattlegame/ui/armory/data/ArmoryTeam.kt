package angelosz.catbattlegame.ui.armory.data

data class ArmoryTeam (
    val teamId: Long,
    val teamName: String,
    val cats: List<SimpleArmoryCatData>
)