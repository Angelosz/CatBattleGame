package angelosz.catbattlegame.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import angelosz.catbattlegame.utils.DataStoreConstants.LAST_CAMPAIGN_SELECTED
import angelosz.catbattlegame.utils.DataStoreConstants.SETTING_AUTO_TARGET
import angelosz.catbattlegame.utils.DataStoreConstants.SETTING_MUSIC_ENABLED
import angelosz.catbattlegame.utils.DataStoreConstants.SETTING_SOUND_EFFECTS_ENABLED
import angelosz.catbattlegame.utils.DataStoreConstants.USER_DATASTORE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = USER_DATASTORE)

class DataStoreRepository(
    private val context: Context
){
    suspend fun saveSelectedCampaign(campaignIndex: Int){
        context.dataStore.edit{
            it[LAST_CAMPAIGN_SELECTED] = campaignIndex.toString()
        }
    }

    suspend fun getSelectedCampaign(): Int = context.dataStore.data.first()[LAST_CAMPAIGN_SELECTED]?.toInt() ?: 0

    val settingsFlow: Flow<PlayerSettings> = context.dataStore.data.map { prefs ->
        PlayerSettings(
            autoTargetSelect = prefs[SETTING_AUTO_TARGET] ?: false,
            musicEnabled = prefs[SETTING_MUSIC_ENABLED] ?: true,
            soundEffectsEnabled = prefs[SETTING_SOUND_EFFECTS_ENABLED] ?: true
        )
    }

    suspend fun getSettings(): PlayerSettings{
        val prefs = context.dataStore.data.first()
        return PlayerSettings(
            autoTargetSelect = prefs[SETTING_AUTO_TARGET] ?: false,
            musicEnabled = prefs[SETTING_MUSIC_ENABLED] ?: true,
            soundEffectsEnabled = prefs[SETTING_SOUND_EFFECTS_ENABLED] ?: true
        )
    }

    suspend fun saveSettings(options: PlayerSettings){
        context.dataStore.edit{
            it[SETTING_AUTO_TARGET] = options.autoTargetSelect
            it[SETTING_MUSIC_ENABLED] = options.musicEnabled
            it[SETTING_SOUND_EFFECTS_ENABLED] = options.soundEffectsEnabled
        }
    }

}

data class PlayerSettings(
    val autoTargetSelect: Boolean = false,
    val musicEnabled: Boolean = true,
    val soundEffectsEnabled: Boolean = true
)