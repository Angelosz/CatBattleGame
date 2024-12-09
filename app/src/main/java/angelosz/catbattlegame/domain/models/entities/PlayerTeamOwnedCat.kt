package angelosz.catbattlegame.domain.models.entities

import androidx.room.Entity


@Entity(primaryKeys = ["teamId", "ownedCatId"], tableName = "playerteam_ownedcat")
data class PlayerTeamOwnedCat(
    val teamId: Int,
    val ownedCatId: Int,
    val position: Int
)