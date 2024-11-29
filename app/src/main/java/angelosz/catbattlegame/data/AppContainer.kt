package angelosz.catbattlegame.data

import android.content.Context
import angelosz.catbattlegame.data.database.AppDatabase
import angelosz.catbattlegame.data.repository.AbilityRepository
import angelosz.catbattlegame.data.repository.CatRepository
import angelosz.catbattlegame.data.repository.OfflineAbilityRepository
import angelosz.catbattlegame.data.repository.OfflineCatDaoRepository

interface AppContainer {
    val catRepository: CatRepository
    val abilityRepository: AbilityRepository
}

class AppDataContainer(context: Context): AppContainer{
    private val database: AppDatabase = AppDatabase.getInstance(context)
    override val catRepository: OfflineCatDaoRepository = OfflineCatDaoRepository(database.catDao())
    override val abilityRepository: OfflineAbilityRepository = OfflineAbilityRepository(database.abilityDao())
}