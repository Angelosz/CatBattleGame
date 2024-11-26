package angelosz.catbattlegame

import android.app.Application
import angelosz.catbattlegame.data.AppContainer
import angelosz.catbattlegame.data.AppDataContainer

class CatBattleApplication: Application() {
    lateinit var container: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}