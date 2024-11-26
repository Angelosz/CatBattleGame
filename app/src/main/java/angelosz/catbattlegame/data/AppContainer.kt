package angelosz.catbattlegame.data

import android.content.Context
import angelosz.catbattlegame.data.database.AppDatabase
import angelosz.catbattlegame.data.repository.OfflineCatDaoRepository

interface AppContainer {
    val repository: OfflineCatDaoRepository
}

class AppDataContainer(context: Context): AppContainer{
    private val database: AppDatabase = AppDatabase.getInstance(context)
    override val repository: OfflineCatDaoRepository = OfflineCatDaoRepository(database.catDao())
}