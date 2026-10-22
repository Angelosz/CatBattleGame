package angelosz.catbattlegame.utils

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object DataStoreConstants{
    const val USER_DATASTORE = "user_data"
    val LAST_CAMPAIGN_SELECTED = stringPreferencesKey("campaign")
    val SETTING_AUTO_TARGET = booleanPreferencesKey("auto_target_select")
    val SETTING_MUSIC_ENABLED = booleanPreferencesKey("setting_music_enabled")
    val SETTING_SOUND_EFFECTS_ENABLED = booleanPreferencesKey("setting_sound_effects_enabled")
}