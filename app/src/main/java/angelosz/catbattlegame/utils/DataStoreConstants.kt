package angelosz.catbattlegame.utils

import androidx.datastore.preferences.core.stringPreferencesKey

object DataStoreConstants{
    const val USER_DATASTORE = "user_data"
    val LAST_CAMPAIGN_SELECTED = stringPreferencesKey("campaign")
}