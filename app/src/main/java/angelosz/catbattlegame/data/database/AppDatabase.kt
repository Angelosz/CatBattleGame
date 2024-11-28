package angelosz.catbattlegame.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import angelosz.catbattlegame.data.dao.AbilityDao
import angelosz.catbattlegame.data.dao.CatDao
import angelosz.catbattlegame.domain.models.entities.Ability
import angelosz.catbattlegame.domain.models.entities.AbilityArmorDamageMultiplier
import angelosz.catbattlegame.domain.models.entities.Cat
import angelosz.catbattlegame.domain.models.entities.CatAbilityCrossRef

@Database(
    entities = [
        Cat::class,
        Ability::class,
        CatAbilityCrossRef::class,
        AbilityArmorDamageMultiplier::class
   ], version = 9)
abstract class AppDatabase : RoomDatabase() {
    abstract fun catDao(): CatDao
    abstract fun abilityDao(): AbilityDao

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