package angelosz.catbattlegame.ui.teambuilder

data class TeamData(
    val teamId: Long,
    val teamName: String,
    val cats: List<BasicCatData>
)
