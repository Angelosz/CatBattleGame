package angelosz.catbattlegame.data.database.initialdata

import angelosz.catbattlegame.data.entities.OwnedCat

class PlayerInitialData {
    val ownedCats = listOf(
        OwnedCat(
            catId = 1,
            level = 1,
            experience = 0
        ),
        OwnedCat(
            catId = 5,
            level = 1,
            experience = 0,
        ),
    )
}