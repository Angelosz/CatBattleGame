package angelosz.catbattlegame.ui.teambuilder

import angelosz.catbattlegame.domain.models.BasicCatData

data class TeamData(
    val teamId: Long,
    val teamName: String,
    val cats: List<BasicCatData>
)
