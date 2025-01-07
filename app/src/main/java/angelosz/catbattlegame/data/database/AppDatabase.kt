package angelosz.catbattlegame.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import angelosz.catbattlegame.data.dao.AbilityDao
import angelosz.catbattlegame.data.dao.BattleChestDao
import angelosz.catbattlegame.data.dao.CampaignDao
import angelosz.catbattlegame.data.dao.CatDao
import angelosz.catbattlegame.data.dao.EnemyCatDao
import angelosz.catbattlegame.data.dao.PlayerDao
import angelosz.catbattlegame.data.entities.Ability
import angelosz.catbattlegame.data.entities.AbilityArmorDamageMultiplier
import angelosz.catbattlegame.data.entities.BattleChest
import angelosz.catbattlegame.data.entities.CampaignChapter
import angelosz.catbattlegame.data.entities.CampaignChapterState
import angelosz.catbattlegame.data.entities.CampaignCompletionState
import angelosz.catbattlegame.data.entities.CampaignEntity
import angelosz.catbattlegame.data.entities.Cat
import angelosz.catbattlegame.data.entities.CatAbilityCrossRef
import angelosz.catbattlegame.data.entities.ChapterEnemy
import angelosz.catbattlegame.data.entities.ChapterReward
import angelosz.catbattlegame.data.entities.EnemyAbility
import angelosz.catbattlegame.data.entities.EnemyCat
import angelosz.catbattlegame.data.entities.EnemyDiscoveryState
import angelosz.catbattlegame.data.entities.OwnedCat
import angelosz.catbattlegame.data.entities.PlayerAccount
import angelosz.catbattlegame.data.entities.PlayerTeam
import angelosz.catbattlegame.data.entities.PlayerTeamOwnedCat

@Database(
    entities = [
        Cat::class,
        Ability::class,
        CatAbilityCrossRef::class,
        AbilityArmorDamageMultiplier::class,
        PlayerAccount::class,
        OwnedCat::class,
        BattleChest::class,
        PlayerTeam::class,
        PlayerTeamOwnedCat::class,
        CampaignEntity::class,
        CampaignCompletionState::class,
        CampaignChapter::class,
        CampaignChapterState::class,
        EnemyCat::class,
        EnemyDiscoveryState::class,
        EnemyAbility::class,
        ChapterEnemy::class,
        ChapterReward::class
    ],
    exportSchema = false,
    version = 31)
abstract class AppDatabase : RoomDatabase() {
    abstract fun catDao(): CatDao
    abstract fun abilityDao(): AbilityDao
    abstract fun playerDao(): PlayerDao
    abstract fun battleChestDao(): BattleChestDao
    abstract fun campaignDao(): CampaignDao
    abstract fun enemyCatDao(): EnemyCatDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "cat_battle_database"
                ).fallbackToDestructiveMigration()
                .build().also { instance = it }
            }
        }
    }
}