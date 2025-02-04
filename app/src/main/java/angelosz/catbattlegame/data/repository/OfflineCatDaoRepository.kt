package angelosz.catbattlegame.data.repository

import angelosz.catbattlegame.data.dao.CatDao
import angelosz.catbattlegame.data.entities.Cat
import angelosz.catbattlegame.domain.enums.CatRarity
import angelosz.catbattlegame.ui.archives.data.SimpleCatData

class OfflineCatDaoRepository(private val catDao: CatDao): CatRepository {
    override suspend fun getAllCats() = catDao.getAllCats()
    override suspend fun getCatsByRarity(rarity: CatRarity) = catDao.getCatsByRarity(rarity)
    override suspend fun getCatById(id: Int) = catDao.getCatById(id)
    override suspend fun insertCats(cats: List<Cat>) = catDao.insertCats(cats)
    override suspend fun clearCatsTable() = catDao.clearCatsTable()
    override suspend fun getCatsById(ids: List<Int>): List<Cat> = catDao.getCatsById(ids)
    override suspend fun getRandomCatByRarity(rarity: CatRarity): Cat = catDao.getRandomCatByRarity(rarity)
    override suspend fun getUnownedCatsOfRarityIds(rarity: CatRarity): List<Int> = catDao.getUnownedCatsOfRarityIds(rarity)
    override suspend fun getSimpleCatDataFromPage(limit: Int, offset: Int): List<SimpleCatData> = catDao.getSimpleCatDataFromPage(limit, offset)
    override suspend fun getFilteredCatDataFromPage(rarity: CatRarity, limit: Int, offset: Int): List<SimpleCatData> = catDao.getFilteredCatDataFromPage(rarity, limit, offset)
    override suspend fun getCount(): Int = catDao.getCount()
}
