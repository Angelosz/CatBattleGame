package angelosz.catbattlegame.data

import android.content.Context
import angelosz.catbattlegame.data.database.AppDatabase
import angelosz.catbattlegame.data.repository.AbilityRepository
import angelosz.catbattlegame.data.repository.BattleChestRepository
import angelosz.catbattlegame.data.repository.CatRepository
import angelosz.catbattlegame.data.repository.OfflineAbilityRepository
import angelosz.catbattlegame.data.repository.OfflineBattleChestRepository
import angelosz.catbattlegame.data.repository.OfflineCatDaoRepository
import angelosz.catbattlegame.data.repository.OfflinePlayerAccountRepository
import angelosz.catbattlegame.data.repository.PlayerAccountRepository

interface AppContainer {
    val catRepository: CatRepository
    val abilityRepository: AbilityRepository
    val playerRepository: PlayerAccountRepository
    val battleChestRepository: BattleChestRepository
}

class AppDataContainer(context: Context): AppContainer{
    private val database: AppDatabase = AppDatabase.getInstance(context)
    override val catRepository: OfflineCatDaoRepository = OfflineCatDaoRepository(database.catDao())
    override val abilityRepository: OfflineAbilityRepository = OfflineAbilityRepository(database.abilityDao())
    override val playerRepository: PlayerAccountRepository = OfflinePlayerAccountRepository(database.playerDao())
    override val battleChestRepository: BattleChestRepository = OfflineBattleChestRepository(database.battleChestDao())
}