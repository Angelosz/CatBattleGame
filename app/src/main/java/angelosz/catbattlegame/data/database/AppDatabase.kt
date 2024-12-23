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
import angelosz.catbattlegame.domain.models.entities.Ability
import angelosz.catbattlegame.domain.models.entities.AbilityArmorDamageMultiplier
import angelosz.catbattlegame.domain.models.entities.BattleChest
import angelosz.catbattlegame.domain.models.entities.Campaign
import angelosz.catbattlegame.domain.models.entities.CampaignChapter
import angelosz.catbattlegame.domain.models.entities.Cat
import angelosz.catbattlegame.domain.models.entities.CatAbilityCrossRef
import angelosz.catbattlegame.domain.models.entities.ChapterEnemy
import angelosz.catbattlegame.domain.models.entities.ChapterReward
import angelosz.catbattlegame.domain.models.entities.EnemyAbility
import angelosz.catbattlegame.domain.models.entities.EnemyCat
import angelosz.catbattlegame.domain.models.entities.OwnedCat
import angelosz.catbattlegame.domain.models.entities.PlayerAccount
import angelosz.catbattlegame.domain.models.entities.PlayerTeam
import angelosz.catbattlegame.domain.models.entities.PlayerTeamOwnedCat

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
        Campaign::class,
        CampaignChapter::class,
        EnemyCat::class,
        EnemyAbility::class,
        ChapterEnemy::class,
        ChapterReward::class
    ],
    exportSchema = false,
    version = 23)
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
                )
                .fallbackToDestructiveMigration()
                .build().also { instance = it }
            }
        }
    }
}