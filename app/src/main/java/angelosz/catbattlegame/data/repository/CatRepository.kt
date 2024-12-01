package angelosz.catbattlegame.data.repository

import angelosz.catbattlegame.domain.models.entities.Cat

interface CatRepository {
    suspend fun getAllCats(): List<Cat>

    suspend fun getCatById(id: Int): Cat

    suspend fun insertCats(cats: List<Cat>)

    suspend fun getCatsById(ids: List<Int>): List<Cat>
}
