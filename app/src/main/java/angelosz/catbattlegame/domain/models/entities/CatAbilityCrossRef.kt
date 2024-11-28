package angelosz.catbattlegame.domain.models.entities

import androidx.room.Entity

@Entity(primaryKeys = ["catId", "abilityId"], tableName = "cat_ability_crossref")
data class CatAbilityCrossRef(
    val catId: Int,
    val abilityId: Int
)
