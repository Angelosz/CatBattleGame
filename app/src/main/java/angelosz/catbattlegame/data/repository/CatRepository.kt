package angelosz.catbattlegame.data.repository

import angelosz.catbattlegame.data.entities.Cat
import angelosz.catbattlegame.domain.enums.CatRarity
import angelosz.catbattlegame.ui.archives.data.SimpleCatData

interface CatRepository {
    suspend fun insertCats(cats: List<Cat>)
    suspend fun clearCatsTable()
    suspend fun getAllCats(): List<Cat>
    suspend fun getCatsByRarity(rarity: CatRarity): List<Cat>
    suspend fun getCatById(id: Int): Cat
    suspend fun getCatsById(ids: List<Int>): List<Cat>
    suspend fun getRandomCatByRarity(rarity: CatRarity): Cat
    suspend fun getUnownedCatsOfRarityIds(rarity: CatRarity): List<Int>
    suspend fun getSimpleCatDataFromPage(limit: Int, offset: Int): List<SimpleCatData>
    suspend fun getFilteredCatDataFromPage(rarity: CatRarity, limit: Int, offset: Int): List<SimpleCatData>
    suspend fun getCount(): Int
}
