package angelosz.catbattlegame.data.repository

import angelosz.catbattlegame.domain.enums.CatRarity
import angelosz.catbattlegame.data.entities.Cat

interface CatRepository {
    suspend fun getAllCats(): List<Cat>

    suspend fun getCatById(id: Int): Cat

    suspend fun insertCats(cats: List<Cat>)

    suspend fun getCatsById(ids: List<Int>): List<Cat>

    suspend fun getRandomCatByRarity(rarity: CatRarity): Cat

    suspend fun getUnownedCatsOfRarityIds(rarity: CatRarity): List<Int>
}
