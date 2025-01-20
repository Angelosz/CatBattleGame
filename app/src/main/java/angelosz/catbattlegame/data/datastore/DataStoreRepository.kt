package angelosz.catbattlegame.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import angelosz.catbattlegame.utils.DataStoreConstants.LAST_CAMPAIGN_SELECTED
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

    suspend fun clearSelectedCampaign() = context.dataStore.edit{
        it.remove(LAST_CAMPAIGN_SELECTED)
    }
}