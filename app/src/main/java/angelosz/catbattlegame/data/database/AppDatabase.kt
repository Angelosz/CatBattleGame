package angelosz.catbattlegame.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import angelosz.catbattlegame.data.dao.CatDao
import angelosz.catbattlegame.domain.models.Cat

@Database(entities = [Cat::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun catDao(): CatDao

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