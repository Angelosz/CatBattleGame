package angelosz.catbattlegame.utils

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object DataStoreConstants{
    const val USER_DATASTORE = "user_data"
    val LAST_CAMPAIGN_SELECTED = stringPreferencesKey("campaign")
    val SETTING_AUTO_TARGET = booleanPreferencesKey("auto_target_select")
}