package angelosz.catbattlegame.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import angelosz.catbattlegame.utils.DataStoreConstants.LAST_CAMPAIGN_SELECTED
import angelosz.catbattlegame.utils.DataStoreConstants.SETTING_AUTO_TARGET
import angelosz.catbattlegame.utils.DataStoreConstants.USER_DATASTORE
import kotlinx.coroutines.flow.first


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = USER_DATASTORE)

class DataStoreRepository(
    val context: Context
){
    suspend fun saveSelectedCampaign(campaignIndex: Int){
        context.dataStore.edit{
            it[LAST_CAMPAIGN_SELECTED] = campaignIndex.toString()
        }
    }

    suspend fun getSelectedCampaign(): Int = context.dataStore.data.first()[LAST_CAMPAIGN_SELECTED]?.toInt() ?: 0

    suspend fun getSettings(): PlayerSettings{
        return PlayerSettings(
            context.dataStore.data.first()[SETTING_AUTO_TARGET] ?: false
        )
    }

    suspend fun saveSettings(options: PlayerSettings){
        context.dataStore.edit{
            it[SETTING_AUTO_TARGET] = options.autoTargetSelect
        }
    }

}

data class PlayerSettings(
    val autoTargetSelect: Boolean = false,
)